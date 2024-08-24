package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import kafkademo.taskmanagersystem.dto.task.CreateTaskDto;
import kafkademo.taskmanagersystem.dto.task.TaskDto;
import kafkademo.taskmanagersystem.dto.task.UpdateTaskDto;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.Task;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.exception.InvalidConstantException;
import kafkademo.taskmanagersystem.exception.UserNotInProjectException;
import kafkademo.taskmanagersystem.kafka.KafkaProducer;
import kafkademo.taskmanagersystem.mapper.TaskMapper;
import kafkademo.taskmanagersystem.repo.TaskRepository;
import kafkademo.taskmanagersystem.service.MessageFormer;
import kafkademo.taskmanagersystem.service.ProjectService;
import kafkademo.taskmanagersystem.service.TaskService;
import kafkademo.taskmanagersystem.service.UserService;
import kafkademo.taskmanagersystem.validation.EnumValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private static final String SCHEDULE = "0 0 9,17 * * *";
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskMapper taskMapper;
    private final MessageFormer messageFormer;
    private final KafkaProducer kafkaProducer;

    @Override
    public TaskDto create(User user, CreateTaskDto createTaskDto) {
        log.info("Creating task with details: {}", createTaskDto);
        Project project = projectService.getProjectById(user, createTaskDto.getProjectId());
        User assignee = userService.findUserProfile(createTaskDto.getUserId());
        project.getUsers().stream()
                .filter(u -> u.equals(assignee))
                .findAny().orElseThrow(() -> {
                    String message = "User with id " + assignee.getId() + " not in project";
                    return new UserNotInProjectException(message);
                });
        Task task = taskMapper.toModel(createTaskDto);
        task.setStatus(Project.Status.INITIATED);
        Task.Priority priority = getPriorityIfValid(createTaskDto.getPriority());
        task.setPriority(priority);
        task.setProject(project);
        task.setUser(assignee);
        kafkaProducer.sendNotificationData(
                messageFormer.formMessageAboutTaskAssigning(project.getName(), task, assignee)
        );
        log.info("Task was created successfully with id: {}", task.getId());
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public List<TaskDto> getAllByProjectId(User user, Long projectId) {
        projectService.getProjectById(user, projectId);
        log.info("Fetching all tasks for projectId: {}", projectId);
        return taskRepository.findAllByProjectId(projectId).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public TaskDto getById(User user, Long id) {
        log.info("Fetching task by id: {}", id);
        return taskMapper.toDto(getTaskById(user, id));
    }

    @Override
    public void deleteById(User user, Long id) {
        log.info("Deleting task by id: {}", id);
        taskRepository.delete(getTaskById(user, id));
    }

    @Override
    public TaskDto updateById(User user, Long id, UpdateTaskDto updateTaskDto) {
        Task task = getTaskById(user, id);
        task.setName(updateTaskDto.getName());
        task.setDescription(updateTaskDto.getDescription());
        task.setDueDate(updateTaskDto.getDueDate());
        validateAndSetEnums(task, updateTaskDto.getStatus(), updateTaskDto.getPriority());
        log.info("Task updated successfully with id: {}", id);
        return taskMapper.toDto(taskRepository.save(task));
    }

    private Task getTaskById(User user, Long id) {
        log.info("Fetching task by id: {}", id);
        Task task = taskRepository.findById(id).orElseThrow(() -> {
            String message = "Task with id " + id + " doesn't exist.";
            return new EntityNotFoundException(message);
        });
        projectService.getProjectById(user, task.getProject().getId());
        return task;
    }

    private Task.Priority getPriorityIfValid(String requestPriority) {
        return EnumValidator.findConstantIfValid(Task.Priority.class, requestPriority)
                .orElseThrow(() -> {
                    String message = "Priority " + requestPriority + " doesn't exist";
                    return new InvalidConstantException(message);
                });
    }

    private void validateAndSetEnums(Task task, String requestStatus, String requestPriority) {
        Project.Status status =
                EnumValidator.findConstantIfValid(Project.Status.class, requestPriority)
                        .orElseThrow(() -> {
                            String message = "Status " + requestStatus + " doesn't exist";
                            return new InvalidConstantException(message);
                        });
        Task.Priority priority = getPriorityIfValid(requestPriority);
        task.setStatus(status);
        task.setPriority(priority);
    }

    @Scheduled(cron = SCHEDULE)
    private void getAndNotifyOverdueTasks() {
        LocalDate today = LocalDate.now();
        List<Task> tasks =
                taskRepository.findTasksWithDueDateTodayAndNotCompleted(today);
        tasks.stream()
                .map(task ->
                        messageFormer.formMessageAboutTaskDeadline(
                                task.getProject().getName(),
                                task,
                                task.getUser()))
                .forEach(kafkaProducer::sendNotificationData);
    }
}

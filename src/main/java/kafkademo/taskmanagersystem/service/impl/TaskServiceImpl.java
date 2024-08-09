package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import kafkademo.taskmanagersystem.dto.task.CreateTaskDto;
import kafkademo.taskmanagersystem.dto.task.TaskDto;
import kafkademo.taskmanagersystem.dto.task.UpdateTaskDto;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.Task;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.exception.InvalidConstantException;
import kafkademo.taskmanagersystem.exception.UserNotInProjectException;
import kafkademo.taskmanagersystem.mapper.TaskMapper;
import kafkademo.taskmanagersystem.repo.TaskRepository;
import kafkademo.taskmanagersystem.service.ProjectService;
import kafkademo.taskmanagersystem.service.TaskService;
import kafkademo.taskmanagersystem.service.UserService;
import kafkademo.taskmanagersystem.validation.EnumValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto create(User user, CreateTaskDto createTaskDto) {
        Project project = projectService.getProjectById(user, createTaskDto.getProjectId());
        User assignee = userService.findUserProfile(createTaskDto.getUserId());
        project.getUsers().stream()
                .filter(u -> u.equals(assignee))
                .findAny().orElseThrow(() -> new UserNotInProjectException(
                        "User with id " + assignee.getId() + " not in project"));
        Task task = taskMapper.toModel(createTaskDto);
        task.setStatus(Project.Status.INITIATED);
        Task.Priority priority = getPriorityIfValid(createTaskDto.getPriority());
        task.setPriority(priority);
        task.setProject(project);
        task.setUser(assignee);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public List<TaskDto> getAllByProjectId(User user, Long projectId) {
        projectService.getProjectById(user, projectId);
        return taskRepository.findAllByProjectId(projectId).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public TaskDto getById(User user, Long id) {
        return taskMapper.toDto(getTaskById(user, id));
    }

    @Override
    public void deleteById(User user, Long id) {
        taskRepository.delete(getTaskById(user, id));
    }

    @Override
    public TaskDto updateById(User user, Long id, UpdateTaskDto updateTaskDto) {
        Task task = getTaskById(user, id);
        task.setName(updateTaskDto.getName());
        task.setDescription(updateTaskDto.getDescription());
        task.setDueDate(updateTaskDto.getDueDate());
        validateAndSetEnums(task, updateTaskDto.getStatus(), updateTaskDto.getPriority());
        return taskMapper.toDto(taskRepository.save(task));
    }

    private Task getTaskById(User user, Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Task with id " + id + " doesn't exist."));
        projectService.getProjectById(user, task.getProject().getId());
        return task;
    }

    private Task.Priority getPriorityIfValid(String requestPriority) {
        return EnumValidator.findConstantIfValid(Task.Priority.class, requestPriority)
                .orElseThrow(() -> new InvalidConstantException("Priority " + requestPriority
                        + " doesn't exist"));
    }

    private void validateAndSetEnums(Task task, String requestStatus, String requestPriority) {
        Project.Status status = EnumValidator.findConstantIfValid(Project.Status.class, requestPriority)
                .orElseThrow(() -> new InvalidConstantException("Status " + requestStatus
                        + " doesn't exist"));
        Task.Priority priority = getPriorityIfValid(requestPriority);
        task.setStatus(status);
        task.setPriority(priority);
    }
}

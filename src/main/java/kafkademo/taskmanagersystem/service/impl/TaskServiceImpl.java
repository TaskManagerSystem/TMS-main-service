package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kafkademo.taskmanagersystem.dto.task.CreateTaskDto;
import kafkademo.taskmanagersystem.dto.task.TaskDto;
import kafkademo.taskmanagersystem.dto.task.UpdateTaskDto;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.Task;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.mapper.TaskMapper;
import kafkademo.taskmanagersystem.repo.ProjectRepository;
import kafkademo.taskmanagersystem.repo.TaskRepository;
import kafkademo.taskmanagersystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto create(User user, CreateTaskDto createTaskDto) {
        Task task = taskMapper.toModel(createTaskDto);
        task.setProject(getProjectIfUserIn(createTaskDto.getProjectId(), user));
        // TODO: remake
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public List<TaskDto> getAllByProjectId(Long projectId) {

        return taskRepository.findAllByProjectId(projectId);
    }

    @Override
    public TaskDto getById(User user, Long id) {
        return taskMapper.toDto(getTaskById(user, id));
    }

    @Override
    public void deleteById(User user, Long id) {
        Task task = getTaskById(user, id);
        taskRepository.delete(task);
    }

    @Override
    public TaskDto updateById(User user, Long id, UpdateTaskDto updateTaskDto) {
        return null;
    }

    private Task getTaskById(User user, Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Task with id " + id + " doesn't exist."));
        getProjectIfUserIn(task.getProject().getId(), user);
        return task;
    }

    private Project getProjectIfUserIn(Long projectId, User user) {
        return projectRepository.findByProjectIdAndUserId(projectId, user.getId()).orElseThrow(() ->
                new EntityNotFoundException("You don't have access to this project."));
    }
}

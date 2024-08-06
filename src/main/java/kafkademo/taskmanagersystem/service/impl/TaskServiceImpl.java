package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kafkademo.taskmanagersystem.dto.task.CreateTaskDto;
import kafkademo.taskmanagersystem.dto.task.TaskDto;
import kafkademo.taskmanagersystem.dto.task.UpdateTaskDto;
import kafkademo.taskmanagersystem.entity.Task;
import kafkademo.taskmanagersystem.mapper.TaskMapper;
import kafkademo.taskmanagersystem.repo.ProjectRepository;
import kafkademo.taskmanagersystem.repo.TaskRepository;
import kafkademo.taskmanagersystem.repo.UserRepository;
import kafkademo.taskmanagersystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto create(CreateTaskDto createTaskDto) {
        Task task = taskMapper.toModel(createTaskDto);
        task.setProject(projectRepository.findById(createTaskDto.getProjectId()).orElseThrow(() ->
                 new EntityNotFoundException("Can't find project with id " + createTaskDto.getProjectId())));
        // TODO: remake
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public TaskDto getById(Long id) {
        return null;
    }

    @Override
    public TaskDto deleteById(Long id) {
        return null;
    }

    @Override
    public TaskDto updateById(Long id, UpdateTaskDto updateTaskDto) {
        return null;
    }
}

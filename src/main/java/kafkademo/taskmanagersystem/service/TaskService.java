package kafkademo.taskmanagersystem.service;

import java.util.List;
import kafkademo.taskmanagersystem.dto.task.CreateTaskDto;
import kafkademo.taskmanagersystem.dto.task.TaskDto;
import kafkademo.taskmanagersystem.dto.task.UpdateTaskDto;

public interface TaskService {
    TaskDto create(CreateTaskDto createTaskDto);

    List<TaskDto> getAllByProjectId(Long projectId);

    TaskDto getById(Long id);

    TaskDto deleteById(Long id);

    TaskDto updateById(Long id, UpdateTaskDto updateTaskDto);
}

package kafkademo.taskmanagersystem.service;

import kafkademo.taskmanagersystem.dto.task.CreateTaskDto;
import kafkademo.taskmanagersystem.dto.task.TaskDto;
import kafkademo.taskmanagersystem.dto.task.UpdateTaskDto;

public interface TaskService {
    TaskDto create(CreateTaskDto createTaskDto);

    TaskDto getById(Long id);

    TaskDto deleteById(Long id);

    TaskDto updateById(Long id, UpdateTaskDto updateTaskDto);
}

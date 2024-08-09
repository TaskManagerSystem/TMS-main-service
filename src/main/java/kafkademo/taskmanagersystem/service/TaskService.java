package kafkademo.taskmanagersystem.service;

import java.util.List;
import kafkademo.taskmanagersystem.dto.task.CreateTaskDto;
import kafkademo.taskmanagersystem.dto.task.TaskDto;
import kafkademo.taskmanagersystem.dto.task.UpdateTaskDto;
import kafkademo.taskmanagersystem.entity.User;

public interface TaskService {
    TaskDto create(User user, CreateTaskDto createTaskDto);

    List<TaskDto> getAllByProjectId(User user, Long projectId);

    TaskDto getById(User user, Long id);

    void deleteById(User user, Long id);

    TaskDto updateById(User user, Long id, UpdateTaskDto updateTaskDto);
}

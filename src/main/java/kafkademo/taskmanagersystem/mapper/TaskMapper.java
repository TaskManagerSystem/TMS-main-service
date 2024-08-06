package kafkademo.taskmanagersystem.mapper;

import kafkademo.taskmanagersystem.config.MapperConfig;
import kafkademo.taskmanagersystem.dto.task.CreateTaskDto;
import kafkademo.taskmanagersystem.dto.task.TaskDto;
import kafkademo.taskmanagersystem.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface TaskMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "project.id", target = "projectId")
    TaskDto toDto(Task task);

    Task toModel(CreateTaskDto createTaskDto);
}

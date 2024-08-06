package kafkademo.taskmanagersystem.mapper;

import kafkademo.taskmanagersystem.config.MapperConfig;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.entity.Project;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {
    ProjectDto toDto(Project project);

    Project toModel(CreateProjectDto createProjectDto);
}

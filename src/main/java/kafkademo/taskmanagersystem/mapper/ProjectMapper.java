package kafkademo.taskmanagersystem.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import kafkademo.taskmanagersystem.config.MapperConfig;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {
    @Mapping(target = "userIds", ignore = true)
    ProjectDto toDto(Project project);

    @Mapping(target = "users", ignore = true)
    Project toModel(CreateProjectDto createProjectDto);

    @AfterMapping
    default void setUserIds(@MappingTarget ProjectDto projectDto, Project project) {
        Set<Long> users = project.getUsers()
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        projectDto.setUserIds(users);
    }

    @AfterMapping
    default void setUserIds(@MappingTarget Project project, CreateProjectDto createProjectDto) {
        Set<User> users = createProjectDto.getUserIds()
                .stream()
                .map(User::new)
                .collect(Collectors.toSet());
        project.setUsers(users);
    }
}

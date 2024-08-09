package kafkademo.taskmanagersystem.service;

import java.util.List;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.dto.project.UpdateProjectDto;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.User;

public interface ProjectService {

    ProjectDto create(User user, CreateProjectDto createProjectDto);

    List<ProjectDto> getByUser(User user);

    ProjectDto getById(User user, Long id);

    ProjectDto updateById(User user, Long id, UpdateProjectDto updateProjectDto);

    void deleteById(User user, Long id);

    Project getProjectById(User user, Long id);
}

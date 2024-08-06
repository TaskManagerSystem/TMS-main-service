package kafkademo.taskmanagersystem.service;

import java.util.List;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.dto.project.UpdateProjectDto;

public interface ProjectService {

    ProjectDto create(CreateProjectDto createProjectDto);

    List<ProjectDto> getByUser(Long userId);

    ProjectDto getById(Long id);

    ProjectDto updateById(Long id, UpdateProjectDto updateProjectDto);

    void deleteById(Long id);
}

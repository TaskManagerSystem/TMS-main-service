package kafkademo.taskmanagersystem.service;

import java.util.List;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;

public interface ProjectService {

    ProjectDto create(CreateProjectDto createProjectDto);

    List<ProjectDto> getByUser(Long userId);

    ProjectDto getById(Long id);

    ProjectDto updateById(Long id);

    ProjectDto deleteById(Long id);
}

package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.dto.project.UpdateProjectDto;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.exception.InvalidStatusException;
import kafkademo.taskmanagersystem.mapper.ProjectMapper;
import kafkademo.taskmanagersystem.repo.ProjectRepository;
import kafkademo.taskmanagersystem.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDto create(CreateProjectDto createProjectDto) {
        Project project = projectMapper.toModel(createProjectDto);
        project.setStatus(Project.Status.INITIATED);
        //TODO: add current user to List<User>
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public List<ProjectDto> getByUser(Long userId) {
        return null;
        //TODO: add method for repo
    }

    @Override
    public ProjectDto getById(Long id) {
        return projectMapper.toDto(getProjectById(id));
    }

    @Override
    public ProjectDto updateById(Long id, UpdateProjectDto updateProjectDto) {
        Project project = getProjectById(id);
        project.setName(updateProjectDto.getName());
        project.setDescription(updateProjectDto.getDescription());
        project.setStartDate(updateProjectDto.getStartDate());
        project.setEndDate(updateProjectDto.getEndDate());
        Project.Status status = toStatusIfValid(updateProjectDto.getStatus());
        project.setStatus(status);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    private Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find project with id " + id));
    }

    private Project.Status toStatusIfValid(String requestStatus) {
        return Arrays.stream(Project.Status.values())
                .filter(status -> status.name().equals(requestStatus))
                .findFirst()
                .orElseThrow(
                        () -> new InvalidStatusException("Status " + requestStatus
                                + " doesn't exist")
                );
    }
}

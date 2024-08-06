package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.entity.Project;
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
        return projectRepository.findByUserId();
    }

    @Override
    public ProjectDto getById(Long id) {
        return projectMapper.toDto(projectRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find project with id " + id)));
    }

    @Override
    public ProjectDto updateById(Long id, CreateProjectDto createProjectDto) {
        Project project = projectRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find project with id " + id));
        project.setName(createProjectDto.getName());
        project.setDescription(createProjectDto.getDescription());
        project.setStartDate(createProjectDto.getStartDate());
        project.setEndDate(createProjectDto.getEndDate());
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}

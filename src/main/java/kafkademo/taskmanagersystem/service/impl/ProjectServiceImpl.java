package kafkademo.taskmanagersystem.service.impl;

import java.util.List;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.repo.ProjectRepository;
import kafkademo.taskmanagersystem.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public ProjectDto create(CreateProjectDto createProjectDto) {
        return null;
        //TODO: add logic
    }

    @Override
    public List<ProjectDto> getByUser(Long userId) {
        return List.of();
        //TODO: add logic
    }

    @Override
    public ProjectDto getById(Long id) {
        return null;
        //TODO: add logic
    }

    @Override
    public ProjectDto updateById(Long id) {
        return null;
        //TODO: add logic
    }

    @Override
    public ProjectDto deleteById(Long id) {
        return null;
        //TODO: add logic
    }
}

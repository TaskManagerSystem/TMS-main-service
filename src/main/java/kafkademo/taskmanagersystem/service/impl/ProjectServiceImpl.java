package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectMembersUpdateDto;
import kafkademo.taskmanagersystem.dto.project.UpdateProjectDto;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.exception.InvalidConstantException;
import kafkademo.taskmanagersystem.exception.InvalidUserIdsException;
import kafkademo.taskmanagersystem.exception.UserNotInProjectException;
import kafkademo.taskmanagersystem.mapper.ProjectMapper;
import kafkademo.taskmanagersystem.repo.ProjectRepository;
import kafkademo.taskmanagersystem.service.ProjectService;
import kafkademo.taskmanagersystem.service.UserService;
import kafkademo.taskmanagersystem.validation.EnumValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;

    @Override
    public ProjectDto create(User user, CreateProjectDto createProjectDto) {
        Set<Long> invalidUserIds = getInvalidUserIds(createProjectDto.getUserIds());
        if (!invalidUserIds.isEmpty()) {
            throw new InvalidUserIdsException("Invalid user ids: " + invalidUserIds);
        }
        Project project = projectMapper.toModel(createProjectDto);
        project.setStatus(Project.Status.INITIATED);
        Set<User> usersInProject = project.getUsers();
        if (!createProjectDto.getUserIds().contains(user.getId())) {
            usersInProject.add(user);
        }
        project.setUsers(usersInProject);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public List<ProjectDto> getByUser(User user) {
        return projectRepository.findAllByUserId(user.getId()).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectDto getById(User user, Long id) {
        return projectMapper.toDto(getProjectById(user, id));
    }

    @Override
    public ProjectDto updateById(User user, Long id, UpdateProjectDto updateProjectDto) {
        Project project = getProjectById(user, id);
        project.setName(updateProjectDto.getName());
        project.setDescription(updateProjectDto.getDescription());
        project.setStartDate(updateProjectDto.getStartDate());
        project.setEndDate(updateProjectDto.getEndDate());
        Project.Status status = getStatusIfValid(updateProjectDto.getStatus());
        project.setStatus(status);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public void deleteById(User user, Long id) {
        projectRepository.delete(getProjectById(user, id));
    }

    @Override
    public Project getProjectById(User user, Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find project with id " + id));
        isUserInProject(user, project);
        return project;
    }

    @Override
    public ProjectDto addMembers(User user, Long projectId, ProjectMembersUpdateDto updateDto) {
        Project project = getProjectById(user, projectId);
        Set<Long> invalidUserIds = getInvalidUserIds(updateDto.getMemberIds());
        if (!invalidUserIds.isEmpty()) {
            throw new InvalidUserIdsException("Invalid user ids: " + invalidUserIds);
        }
        Set<User> usersForAdding = updateDto.getMemberIds()
                .stream()
                .map(User::new)
                .collect(Collectors.toSet());
        project.getUsers().addAll(usersForAdding);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public ProjectDto deleteMembers(User user, Long projectId, ProjectMembersUpdateDto updateDto) {
        return null;
    }

    private Project.Status getStatusIfValid(String requestStatus) {
        return EnumValidator.findConstantIfValid(Project.Status.class, requestStatus)
                .orElseThrow(() -> new InvalidConstantException("Status " + requestStatus
                + " doesn't exist"));
    }

    private void isUserInProject(User user, Project project) {
        if (project.getUsers().stream().noneMatch(u -> u.getId().equals(user.getId()))) {
            throw new UserNotInProjectException("Access to project with id "
                    + project.getId() + " is forbidden.");
        }
    }

    private Set<Long> getInvalidUserIds(Set<Long> userIds) {
        Set<Long> allUserIds = userService.getAllUserIds();
        Set<Long> invalidUserIds = new HashSet<>();
        userIds.forEach(id -> {
            if (!allUserIds.contains(id)) {
                invalidUserIds.add(id);
            }
        });
        return invalidUserIds;
    }
}

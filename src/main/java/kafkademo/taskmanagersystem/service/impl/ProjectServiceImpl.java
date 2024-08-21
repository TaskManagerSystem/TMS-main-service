package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import kafkademo.taskmanagersystem.dto.NotificationData;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectMembersUpdateDto;
import kafkademo.taskmanagersystem.dto.project.UpdateProjectDto;
import kafkademo.taskmanagersystem.entity.Project;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.exception.InvalidConstantException;
import kafkademo.taskmanagersystem.exception.InvalidUserIdsException;
import kafkademo.taskmanagersystem.exception.UserNotInProjectException;
import kafkademo.taskmanagersystem.kafka.KafkaProducer;
import kafkademo.taskmanagersystem.mapper.ProjectMapper;
import kafkademo.taskmanagersystem.repo.ProjectRepository;
import kafkademo.taskmanagersystem.service.ProjectService;
import kafkademo.taskmanagersystem.service.UserService;
import kafkademo.taskmanagersystem.validation.EnumValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final KafkaProducer kafkaProducer;
    @Override
    public ProjectDto create(User user, CreateProjectDto createProjectDto) {
        Set<Long> invalidUserIds = getInvalidUserIds(createProjectDto.getUserIds());
        if (!invalidUserIds.isEmpty()) {
            String message = "Invalid user ids: " + invalidUserIds;
            log.error(message);
            throw new InvalidUserIdsException(message);
        }
        Project project = projectMapper.toModel(createProjectDto);
        project.setStatus(Project.Status.INITIATED);
        Set<User> usersInProject = project.getUsers();
        if (!createProjectDto.getUserIds().contains(user.getId())) {
            usersInProject.add(user);
        }
        project.setUsers(usersInProject);
        log.info("Project created with id: {}", project.getId());
        sendNotificationData(project);
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
        log.info("Projects updated with id: {}", id);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public void deleteById(User user, Long id) {
        projectRepository.delete(getProjectById(user, id));
    }

    @Override
    public Project getProjectById(User user, Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> {
            String message = "Can't find project with id " + id;
            log.error(message);
            return new EntityNotFoundException(message);
        });
        isUserInProject(user, project);
        return project;
    }

    @Override
    public ProjectDto addMembers(User user, Long projectId, ProjectMembersUpdateDto updateDto) {
        log.info("Adding members to project with id: {}. Member IDs: {}",
                projectId, updateDto.getMemberIds());
        Project project = getProjectById(user, projectId);
        Set<Long> invalidUserIds = getInvalidUserIds(updateDto.getMemberIds());
        if (!invalidUserIds.isEmpty()) {
            String message = "Invalid user ids: " + invalidUserIds;
            log.error(message);
            throw new InvalidUserIdsException(message);
        }
        Set<User> usersForAdding = updateDto.getMemberIds()
                .stream()
                .map(User::new)
                .collect(Collectors.toSet());
        project.getUsers().addAll(usersForAdding);
        log.info("Members added to project: {}. Updated user list: {}",
                projectId, project.getUsers());
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public ProjectDto deleteMembers(User user, Long projectId, ProjectMembersUpdateDto updateDto) {
        log.info("Deleting members from project with id: {}. Member IDs: {}",
                projectId, updateDto.getMemberIds());

        Project project = getProjectById(user, projectId);
        Set<Long> invalidUserIds = getInvalidUserIds(updateDto.getMemberIds());
        if (!invalidUserIds.isEmpty()) {
            String message = "Invalid user ids: " + invalidUserIds;
            log.error(message);
            throw new InvalidUserIdsException(message);
        }
        Set<User> usersForRemoving = userService.findAllByIdIn(updateDto.getMemberIds());
        usersForRemoving.remove(user);
        project.getUsers().removeAll(usersForRemoving);
        log.info("Members removed from project: {}. Updated user list: {}",
                projectId, project.getUsers());

        return projectMapper.toDto(projectRepository.save(project));
    }

    private void sendNotificationData(Project project) {
        String topic = "Project created";
        String message = String.format("A new project has been created: ID=%d, Name=%s, DueDate=%s",
                project.getId(), project.getName(), project.getStartDate());

        for (User user : project.getUsers()) {
            NotificationData notificationData = NotificationData.builder()
                    .email(user.getEmail())
                    .chatId(user.getChatId().toString())
                    .message(message)
                    .topic(topic)
                    .build();
            kafkaProducer.sendNotificationData(notificationData);
        }
    }

    private Project.Status getStatusIfValid(String requestStatus) {
        return EnumValidator.findConstantIfValid(Project.Status.class, requestStatus)
                .orElseThrow(() -> {
                    String message = "Status " + requestStatus
                            + " doesn't exist";
                    log.error(message);
                    return new InvalidConstantException(message);
                });
    }

    private void isUserInProject(User user, Project project) {
        if (project.getUsers().stream().noneMatch(u -> u.getId().equals(user.getId()))) {
            String message = "Access to project with id " + project.getId() + " is forbidden.";
            log.error(message);

            throw new UserNotInProjectException(message);
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

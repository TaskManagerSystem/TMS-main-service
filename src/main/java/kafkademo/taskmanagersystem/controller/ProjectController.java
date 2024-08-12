package kafkademo.taskmanagersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectMembersUpdateDto;
import kafkademo.taskmanagersystem.dto.project.UpdateProjectDto;
import kafkademo.taskmanagersystem.dto.task.TaskDto;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.service.ProjectService;
import kafkademo.taskmanagersystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
@Tag(name = "Project management", description = "Endpoints for managing projects")
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new project",
            description = "Create a new project")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDto create(@AuthenticationPrincipal User user,
                             @RequestBody @Valid CreateProjectDto createProjectDto) {
        return projectService.create(user, createProjectDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project by id",
            description = "Get project by specific id")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ProjectDto findById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return projectService.getById(user, id);
    }

    @GetMapping
    @Operation(summary = "Get all projects by user",
            description = "Get a list of all project by user")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProjectDto> findAllByUser(@AuthenticationPrincipal User user) {
        return projectService.getByUser(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete project by id",
            description = "Delete project by specific id")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@AuthenticationPrincipal User user, @PathVariable Long id) {
        projectService.deleteById(user, id);
    }

    @DeleteMapping("/{projectId}/members")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Delete members from project",
            description = "Delete members from project")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDto deleteMembers(@AuthenticationPrincipal User user,
                                    @PathVariable Long projectId,
                                    @RequestBody ProjectMembersUpdateDto updateDto) {
        return projectService.deleteMembers(user, projectId, updateDto);
    }

    @PutMapping("/{projectId}/members")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Add members from project",
            description = "Add members from project")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDto addMembers(@AuthenticationPrincipal User user,
                                 @PathVariable Long projectId,
                                 @RequestBody ProjectMembersUpdateDto updateDto) {
        return projectService.addMembers(user, projectId, updateDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update project by id",
            description = "Update project by specific id")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDto updateById(@AuthenticationPrincipal User user,
                                 @PathVariable Long id,
                                 @RequestBody @Valid UpdateProjectDto updateProjectDto) {
        return projectService.updateById(user, id, updateProjectDto);
    }

    @GetMapping("/{projectId}/tasks")
    @Operation(summary = "Get all tasks by project id",
            description = "Get all tasks by project id")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<TaskDto> getAllByProjectId(@AuthenticationPrincipal User user,
                                           @PathVariable Long projectId) {
        return taskService.getAllByProjectId(user, projectId);
    }
}

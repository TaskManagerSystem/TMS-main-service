package kafkademo.taskmanagersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.dto.project.UpdateProjectDto;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
@Tag(name = "Project management", description = "Endpoints for managing projects")
public class ProjectController {
    private final ProjectService projectService;

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

    @PutMapping("/{id}")
    @Operation(summary = "Update project by id",
            description = "Update project by specific id")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDto updateById(@AuthenticationPrincipal User user,
                                 @PathVariable Long id,
                                 @RequestBody @Valid UpdateProjectDto updateProjectDto) {
        return projectService.updateById(user, id, updateProjectDto);
    }
}

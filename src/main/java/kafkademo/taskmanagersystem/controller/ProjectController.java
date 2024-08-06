package kafkademo.taskmanagersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kafkademo.taskmanagersystem.dto.project.CreateProjectDto;
import kafkademo.taskmanagersystem.dto.project.ProjectDto;
import kafkademo.taskmanagersystem.dto.project.UpdateProjectDto;
import kafkademo.taskmanagersystem.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new project",
            description = "Create a new project")
    public ProjectDto create(@RequestBody @Valid CreateProjectDto createProjectDto) {
        return projectService.create(createProjectDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project by id",
            description = "Get project by specific id")
    public ProjectDto findById(@PathVariable Long id) {
        return projectService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get all projects by user",
            description = "Get a list of all project by user")
    public List<ProjectDto> findAllByUser() {
        //TODO:  return projectService.getByUser();
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete project by id",
            description = "Delete project by specific id")
    public void delete(@PathVariable Long id) {
        projectService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update project by id",
            description = "Update project by specific id")
    public ProjectDto updateById(
            @PathVariable Long id, @RequestBody @Valid UpdateProjectDto updateProjectDto) {
        return projectService.updateById(id, updateProjectDto);
    }
}

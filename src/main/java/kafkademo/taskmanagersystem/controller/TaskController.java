package kafkademo.taskmanagersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kafkademo.taskmanagersystem.dto.task.CreateTaskDto;
import kafkademo.taskmanagersystem.dto.task.TaskDto;
import kafkademo.taskmanagersystem.dto.task.UpdateTaskDto;
import kafkademo.taskmanagersystem.service.TaskService;
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
@RequestMapping("/tasks")
@Tag(name = "Tasks management", description = "Endpoints for managing tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create task", description = "Create a new task")
    public TaskDto create(CreateTaskDto createTaskDto) {
        return taskService.create(createTaskDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id", description = "Get task by specific id")
    public TaskDto getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete task by id",
            description = "Delete task by specific id")
    public void delete(@PathVariable Long id) {
        taskService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task by id",
            description = "Update task by specific id")
    public TaskDto updateById(
            @PathVariable Long id, @RequestBody @Valid UpdateTaskDto updateTaskDto) {
        return taskService.updateById(id, updateTaskDto);
    }
}

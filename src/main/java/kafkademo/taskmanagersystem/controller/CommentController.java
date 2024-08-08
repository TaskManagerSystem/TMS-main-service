package kafkademo.taskmanagersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kafkademo.taskmanagersystem.dto.comment.CommentRequestDto;
import kafkademo.taskmanagersystem.dto.comment.CommentResponseDto;
import kafkademo.taskmanagersystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comment management", description = "Endpoints for managing comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "Add comments to task",
            description = "Add comments to task")
    public CommentResponseDto addCommentToTask(@Valid @RequestBody CommentRequestDto requestDto) {
        return commentService.addComment(requestDto);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Return list of comments",
            description = "Return list of comments for specific task")
    public List<CommentResponseDto> getCommentsByTaskId(@PathVariable Long taskId) {
        return commentService.getCommentsByTaskId(taskId);
    }
}

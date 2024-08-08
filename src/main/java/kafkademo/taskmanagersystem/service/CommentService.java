package kafkademo.taskmanagersystem.service;

import java.util.List;
import kafkademo.taskmanagersystem.dto.comment.CommentRequestDto;
import kafkademo.taskmanagersystem.dto.comment.CommentResponseDto;

public interface CommentService {
    CommentResponseDto addComment(CommentRequestDto requestDto);

    List<CommentResponseDto> getCommentsByTaskId(Long id);
}

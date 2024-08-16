package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import kafkademo.taskmanagersystem.dto.comment.CommentRequestDto;
import kafkademo.taskmanagersystem.dto.comment.CommentResponseDto;
import kafkademo.taskmanagersystem.entity.Comment;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.mapper.CommentMapper;
import kafkademo.taskmanagersystem.repo.CommentRepository;
import kafkademo.taskmanagersystem.repo.UserRepository;
import kafkademo.taskmanagersystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    //private final TaskRepository taskRepository;

    @Override
    public CommentResponseDto addComment(CommentRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUser().getId())
                .orElseThrow(() -> {
                    String message = "Can not find user by id: " + requestDto.getUser().getId();
                    log.error(message);
                    return new EntityNotFoundException(message);
                });
        //Task task = //TODO: complete this method after adding task service
        Comment comment = new Comment();
        comment.setText(requestDto.getText());
        comment.setUser(user);
        //comment.setTask();
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> getCommentsByTaskId(Long id) {
        return commentRepository.findByTaskId(id)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }
}

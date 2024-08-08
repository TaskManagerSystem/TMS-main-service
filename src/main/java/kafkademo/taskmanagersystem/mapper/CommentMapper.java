package kafkademo.taskmanagersystem.mapper;

import kafkademo.taskmanagersystem.config.MapperConfig;
import kafkademo.taskmanagersystem.dto.comment.CommentResponseDto;
import kafkademo.taskmanagersystem.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {

    CommentResponseDto toDto(Comment comment);

}

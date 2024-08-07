package kafkademo.taskmanagersystem.mapper;

import kafkademo.taskmanagersystem.config.MapperConfig;
import kafkademo.taskmanagersystem.dto.user.request.RegisterUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.response.ResponseUserDto;
import kafkademo.taskmanagersystem.entity.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    ResponseUserDto toDto(User user);

    User toEntity(UpdateUserRequestDto updateDto);

    User toEntity(RegisterUserRequestDto registerDto);
}

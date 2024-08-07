package kafkademo.taskmanagersystem.mapper;

import kafkademo.taskmanagersystem.config.MapperConfig;
import kafkademo.taskmanagersystem.dto.user.request.RegisterUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.response.ResponseUserDto;
import kafkademo.taskmanagersystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = MapperConfig.class)
public interface UserMapper {

    @Mapping(source = "user.nickName", target = "nickName")
    ResponseUserDto toDto(User user);

    @Mapping(source = "registerDto.nickName", target = "nickName")
    User toEntity(RegisterUserRequestDto registerDto);
}

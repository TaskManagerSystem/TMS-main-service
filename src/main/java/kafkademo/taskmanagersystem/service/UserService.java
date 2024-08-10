package kafkademo.taskmanagersystem.service;

import java.util.Set;
import kafkademo.taskmanagersystem.dto.user.request.RegisterUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRoleDto;
import kafkademo.taskmanagersystem.dto.user.response.ResponseUserDto;
import kafkademo.taskmanagersystem.entity.User;

public interface UserService {

    ResponseUserDto register(RegisterUserRequestDto requestDto);

    ResponseUserDto updateUserRole(UpdateUserRoleDto updateDto, Long userId);

    ResponseUserDto updateUserProfile(UpdateUserRequestDto updateDto, Long userId);

    ResponseUserDto getUserProfile(Long userId);

    Set<Long> getAllUserIds();

    User findUserProfile(Long userId);

    Set<User> findAllByIdIn(Set<Long> userIds);
}

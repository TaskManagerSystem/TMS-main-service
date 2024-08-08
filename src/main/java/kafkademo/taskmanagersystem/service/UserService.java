package kafkademo.taskmanagersystem.service;

import kafkademo.taskmanagersystem.dto.user.request.RegisterUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRoleDto;
import kafkademo.taskmanagersystem.dto.user.response.ResponseUserDto;

import java.util.Set;

public interface UserService {

    ResponseUserDto register(RegisterUserRequestDto requestDto);

    ResponseUserDto updateUserRole(UpdateUserRoleDto updateDto, Long userId);

    ResponseUserDto updateUserProfile(UpdateUserRequestDto updateDto, Long userId);

    ResponseUserDto getUserProfile(Long userId);

    Set<Long> getAllUserIds();
}

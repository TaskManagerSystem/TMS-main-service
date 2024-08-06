package kafkademo.taskmanagersystem.service;

import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.response.ResponseUserDto;
import kafkademo.taskmanagersystem.entity.User;

public interface UserService {

    ResponseUserDto updateUserRole(UpdateUserRequestDto updateDto);

    ResponseUserDto updateUserProfile(UpdateUserRequestDto updateDto);

    ResponseUserDto getUserProfile();
}

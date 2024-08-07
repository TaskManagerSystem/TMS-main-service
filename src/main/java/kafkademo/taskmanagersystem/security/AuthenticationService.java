package kafkademo.taskmanagersystem.security;

import kafkademo.taskmanagersystem.dto.user.request.UserLoginRequestDto;
import kafkademo.taskmanagersystem.dto.user.response.UserLoginResponseDto;

public interface AuthenticationService {
     UserLoginResponseDto login(UserLoginRequestDto requestDto);
}

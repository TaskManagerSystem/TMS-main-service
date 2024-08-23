package kafkademo.taskmanagersystem.security;

import com.example.dto.IsVerificationDto;
import kafkademo.taskmanagersystem.dto.user.request.UserLoginRequestDto;
import kafkademo.taskmanagersystem.dto.user.response.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto login(UserLoginRequestDto requestDto);

    void tokenValidate(IsVerificationDto dto);
}

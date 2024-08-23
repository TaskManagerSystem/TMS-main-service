package kafkademo.taskmanagersystem.security;

import com.example.dto.IsVerificationDto;
import kafkademo.taskmanagersystem.dto.user.request.UserLoginRequestDto;
import kafkademo.taskmanagersystem.dto.user.response.UserLoginResponseDto;
import kafkademo.taskmanagersystem.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final KafkaProducer producer;

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(), requestDto.getPassword())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }

    @Override
    public void tokenValidate(IsVerificationDto dto) {
        try {
            log.info("Got token from attachment: " + dto.getToken());
            dto.setValid(jwtUtil.isValidToken(dto.getToken()));
            producer.sendResponseToAttachmentService(dto);
        } catch (Exception e) {
            log.error("Error processing token: " + dto.getToken(), e);
        }
    }
}

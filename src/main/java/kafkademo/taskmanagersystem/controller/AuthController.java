package kafkademo.taskmanagersystem.controller;

import jakarta.validation.Valid;
import kafkademo.taskmanagersystem.dto.user.request.RegisterUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UserLoginRequestDto;
import kafkademo.taskmanagersystem.dto.user.response.ResponseUserDto;
import kafkademo.taskmanagersystem.dto.user.response.UserLoginResponseDto;
import kafkademo.taskmanagersystem.security.AuthenticationService;
import kafkademo.taskmanagersystem.service.UserService;
import kafkademo.taskmanagersystem.validation.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final VerificationService verificationService;

    @PostMapping("/registration")
    public ResponseUserDto register(@Valid @RequestBody RegisterUserRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.login(requestDto);
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam String token) {
        boolean isVerified = verificationService.verifyData(token);
        if (isVerified) {
            log.info("Token verified successfully for token: {}", token);
            return ResponseEntity.ok("Email successfully linked");
        } else {
            log.warn("Invalid token received: {}", token);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Invalid or expired token");
        }
    }
}

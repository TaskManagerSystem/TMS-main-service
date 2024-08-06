package kafkademo.taskmanagersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRoleDto;
import kafkademo.taskmanagersystem.dto.user.response.ResponseUserDto;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operations related to user management")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Update user role",
            description = "Allows an admin to update the role of a user")
    @PutMapping("/{userId}")
    public ResponseUserDto updateUserRole(@Valid @RequestBody UpdateUserRoleDto updateDto,
                                          @PathVariable Long userId) {
        return userService.updateUserRole(updateDto, userId);
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user's profile",
            description = "Retrieve the profile information of the currently authenticated user")
    public ResponseUserDto getMyProfile(User user) { //TODO: add @AuthorizationPrincipal annotation
        return userService.getUserProfile(user.getId());
    }

    @PutMapping("/me")
    @Operation(summary = "Update current user's profile",
            description = "Update the profile information of the currently authenticated user")
    public ResponseUserDto updateProfile(@RequestBody @Valid UpdateUserRequestDto updateDto,
                                          //TODO: add @AuthorizationPrincipal annotation
                                          User user) {
        return userService.updateUserProfile(updateDto, user.getId());
    }
}

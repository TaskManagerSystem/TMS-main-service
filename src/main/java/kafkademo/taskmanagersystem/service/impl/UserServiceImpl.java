package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kafkademo.taskmanagersystem.dto.user.request.RegisterUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRoleDto;
import kafkademo.taskmanagersystem.dto.user.response.ResponseUserDto;
import kafkademo.taskmanagersystem.entity.Role;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.exception.RegistrationException;
import kafkademo.taskmanagersystem.mapper.UserMapper;
import kafkademo.taskmanagersystem.repo.RoleRepository;
import kafkademo.taskmanagersystem.repo.UserRepository;
import kafkademo.taskmanagersystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public ResponseUserDto register(RegisterUserRequestDto requestDto) {
        if (userRepository.findUserByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with email: "
                    + requestDto.getEmail() + " does already exist");
        }
        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findAllByRoleName(Role.RoleName.USER));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public ResponseUserDto updateUserRole(UpdateUserRoleDto updateDto, Long userId) {
        User user = findUserProfile(userId);
        // TODO: add updating to user role
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public ResponseUserDto updateUserProfile(UpdateUserRequestDto updateDto, Long userId) {
        User user = findUserProfile(userId);
        user.setUserName(updateDto.getUserName());
        user.setFirstName(updateDto.getUserFirstName());
        user.setLastName(updateDto.getUserLastName());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public ResponseUserDto getUserProfile(Long userId) {
        User user = findUserProfile(userId);
        return userMapper.toDto(user);
    }

    private User findUserProfile(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can not find user's profile by id: " + userId));
    }
}

package kafkademo.taskmanagersystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRoleDto;
import kafkademo.taskmanagersystem.dto.user.response.ResponseUserDto;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.mapper.UserMapper;
import kafkademo.taskmanagersystem.repo.UserRepository;
import kafkademo.taskmanagersystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ResponseUserDto updateUserRole(UpdateUserRoleDto updateDto, Long userId) {
        User user = findUserProfile(userId);
//        user.setRole(updateDto.getRole); // ЦЕ ДОПИШУ ПІСЛЯ ДОДАВАННЯ РЕГІСТРАЦІЇ ТА АУТЕНТИФІКАЦІЇ
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
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can not find user's profile by id: " + userId));
        return user;
    }
}

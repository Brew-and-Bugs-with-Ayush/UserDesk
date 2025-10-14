package com.ayush.userdesk.service;

import com.ayush.userdesk.domain.dto.ChangePasswordDto;
import com.ayush.userdesk.domain.dto.UserRequestDto;
import com.ayush.userdesk.domain.dto.UserResponseDto;
import com.ayush.userdesk.domain.entity.User;
import com.ayush.userdesk.exceptionhandling.InvalidCurrentPasswordException;
import com.ayush.userdesk.mapper.UserMapper;
import com.ayush.userdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponseDto getUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User not found with id: " + id));

        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto getUserByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found : " + username));

        return userMapper.toUserResponseDto(user);
    }

    @Transactional
    public UserResponseDto changePassword(Long id, ChangePasswordDto changePasswordDto) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User not found with id: " + id));

        if (!passwordEncoder.matches(changePasswordDto.currentPassword(), user.getPassword())) {
            throw new InvalidCurrentPasswordException();
        }

        if (!changePasswordDto.newPassword().equals(changePasswordDto.confirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));

        return userMapper.toUserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        Optional.ofNullable(dto.username())
                .filter(username -> !username.equals(user.getUsername()))
                .ifPresent(user::setUsername);

        Optional.ofNullable(dto.email())
                .filter(email -> !email.equals(user.getEmail()))
                .ifPresent(user::setEmail);

        return userMapper.toUserResponseDto(user);
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User not found with id: " + id));
        userRepo.delete(user);
    }
}

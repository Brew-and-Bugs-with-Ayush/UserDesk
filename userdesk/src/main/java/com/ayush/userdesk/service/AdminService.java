package com.ayush.userdesk.service;

import com.ayush.userdesk.domain.dto.RegisterRequestDto;
import com.ayush.userdesk.domain.dto.RegisterResponseDto;
import com.ayush.userdesk.domain.dto.UserRequestDto;
import com.ayush.userdesk.domain.dto.UserResponseDto;
import com.ayush.userdesk.domain.entity.User;
import com.ayush.userdesk.enums.Role;
import com.ayush.userdesk.mapper.UserMapper;
import com.ayush.userdesk.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponseDto registerNormalUser(@Valid RegisterRequestDto requestDto) {

        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.getRoles().add(Role.USER);

        try {
            User savedUser = userRepo.save(user);
            return userMapper.toRegisterResponseDto(savedUser);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists", ex);
        }
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    public UserResponseDto getUserById(Long id) {

        final String message ="User not found with id: ";
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(message + id));
        return userMapper.toUserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        user.setUsername(userRequestDto.username());
        user.setEmail(userRequestDto.email());

        User updatedUser = userRepo.save(user);
        return userMapper.toUserResponseDto(updatedUser);
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (!userRepo.existsById(id)) {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
        userRepo.deleteById(id);
    }

    @Transactional
    public UserResponseDto assignRole(Long id, Set<String> roles) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        Set<Role> newRoles = new HashSet<>();
        for (String role : roles) {
            try {
                newRoles.add(Role.valueOf(role.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role: " + role + ". Allowed roles: " + Arrays.toString(Role.values()));
            }
        }

        user.getRoles().addAll(newRoles);

        User updatedUser = userRepo.save(user);
        return userMapper.toUserResponseDto(updatedUser);
    }

}

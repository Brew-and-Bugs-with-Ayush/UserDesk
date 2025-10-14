package com.ayush.userdesk.mapper;

import com.ayush.userdesk.domain.dto.LoginResponseDto;
import com.ayush.userdesk.domain.dto.RegisterRequestDto;
import com.ayush.userdesk.domain.dto.RegisterResponseDto;
import com.ayush.userdesk.domain.dto.UserResponseDto;
import com.ayush.userdesk.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles()
        );
    }


    public RegisterResponseDto toRegisterResponseDto(User user){
        return new RegisterResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles()
        );
    }

    public User toEntity(RegisterRequestDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }

}


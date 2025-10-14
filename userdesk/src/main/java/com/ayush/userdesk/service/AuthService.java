package com.ayush.userdesk.service;

import com.ayush.userdesk.domain.dto.RegisterRequestDto;
import com.ayush.userdesk.domain.dto.RegisterResponseDto;
import com.ayush.userdesk.domain.entity.User;
import com.ayush.userdesk.enums.Role;
import com.ayush.userdesk.mapper.UserMapper;
import com.ayush.userdesk.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponseDto registerNormalUser(RegisterRequestDto requestDto) {
        if (userRepo.existsByEmail(requestDto.email())) {
            throw new DataIntegrityViolationException(requestDto.email());
        }

        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setRoles(Set.of(Role.USER));

        User savedUser = userRepo.save(user);
        return userMapper.toRegisterResponseDto(savedUser);
    }

    public UserDetails loginUser(String email, String password) {

        userRepo.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        return (UserDetails) authentication.getPrincipal();
    }
}

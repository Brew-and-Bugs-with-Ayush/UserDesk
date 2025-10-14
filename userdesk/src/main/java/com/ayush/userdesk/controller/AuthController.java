package com.ayush.userdesk.controller;

import com.ayush.userdesk.domain.dto.*;
import com.ayush.userdesk.domain.entity.User;
import com.ayush.userdesk.jwt.JwtService;
import com.ayush.userdesk.repository.UserRepository;
import com.ayush.userdesk.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepo;


    @PostMapping("/register-normal-user")
    public ResponseEntity<RegisterResponseDto> registerUser(
            @Valid @RequestBody RegisterRequestDto requestDto) {

        RegisterResponseDto responseDto = authService.registerNormalUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {

        UserDetails userDetails = authService.loginUser(requestDto.email(), requestDto.password());

        String tokenValue = jwtService.generateToken(userDetails);

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .token(tokenValue)
                .expiresInSeconds(3600L) // 1 hour in seconds
                .build();

        //  HTTP-only cookie with JWT
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", tokenValue)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(responseDto);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie clearCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)  // immediately expire
                .sameSite("Strict")
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                .build();
    }

    @GetMapping("/getCurrentLoggedInUser")
    public ResponseEntity<UserResponseDto> getCurrentLoggedInUser(
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserResponseDto dto = new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles()
        );

        return ResponseEntity.ok(dto);
    }
}

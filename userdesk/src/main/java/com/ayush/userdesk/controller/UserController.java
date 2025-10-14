package com.ayush.userdesk.controller;

import com.ayush.userdesk.domain.dto.ChangePasswordDto;
import com.ayush.userdesk.domain.dto.UserRequestDto;
import com.ayush.userdesk.domain.dto.UserResponseDto;
import com.ayush.userdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("#id == authentication.principal.id")
    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto userById = userService.getUserById(id);

        return ResponseEntity.ok(userById);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @GetMapping("{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        UserResponseDto userByUsername = userService.getUserByUsername(username);
        return ResponseEntity.ok(userByUsername);
    }

    @PreAuthorize("#id == authentication.principal.id")
    @PutMapping("/change-password/{id}")
    public ResponseEntity<UserResponseDto> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordDto changePasswordDto
    ) {
        UserResponseDto userResponseDto = userService.changePassword(id, changePasswordDto);
        return ResponseEntity.ok().body(userResponseDto);
    }

    @PreAuthorize("#id == authentication.principal.id")
    @PutMapping("/update-user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto userRequestDto
    ) {
        UserResponseDto userResponseDto = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok().body(userResponseDto);
    }

    @PreAuthorize("#id == authentication.principal.id")
    @DeleteMapping("delete-user/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().body(true);
    }
}

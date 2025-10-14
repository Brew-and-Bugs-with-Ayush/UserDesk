package com.ayush.userdesk.controller;

import com.ayush.userdesk.domain.dto.RegisterRequestDto;
import com.ayush.userdesk.domain.dto.RegisterResponseDto;
import com.ayush.userdesk.domain.dto.UserRequestDto;
import com.ayush.userdesk.domain.dto.UserResponseDto;
import com.ayush.userdesk.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register-user")
    public ResponseEntity<RegisterResponseDto> registerUser(
            @Valid @RequestBody RegisterRequestDto requestDto) {

        RegisterResponseDto responseDto = adminService.registerNormalUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> allUsers = adminService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = adminService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = adminService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        adminService.deleteUserById(id);
        return ResponseEntity.ok(true);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/assign-role/{id}")
    public ResponseEntity<UserResponseDto> assignRole(
            @PathVariable Long id,
            @RequestParam Set<String> roles) {
        UserResponseDto updatedUser = adminService.assignRole(id, roles);
        return ResponseEntity.ok(updatedUser);
    }
}

package com.ayush.userdesk.domain.dto;

import com.ayush.userdesk.enums.Role;

import java.util.Set;

public record RegisterResponseDto(
        String userId,
        String username,
        String email,
        Set<Role> roles
) {
}

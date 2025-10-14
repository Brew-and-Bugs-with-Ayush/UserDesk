package com.ayush.userdesk.domain.dto;

public record ChangePasswordDto(
        String currentPassword,
        String newPassword,
        String confirmPassword
) {
}

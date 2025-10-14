package com.ayush.userdesk.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRequestDto(
        @NotBlank(message = "Username required it should not be empty")
        @Size(min = 3, max = 10)
        String username,

        @Email(message = "Enter a valid email address")
        @NotBlank(message = "Email required it should not be empty")
        String email
) {
}

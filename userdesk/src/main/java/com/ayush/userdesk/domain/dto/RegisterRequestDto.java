package com.ayush.userdesk.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public record RegisterRequestDto(

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 10, message = "Username must be between {min} and {max} characters")
        String username,

        @Email(message = "Enter a valid email address")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 10, message = "Password must be between {min} and {max} characters")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,10}$",
                message = "Password must contain at least one uppercase, one lowercase, one number, and one special character"
        )
        String password
) {}


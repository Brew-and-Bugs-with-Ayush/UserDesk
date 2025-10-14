package com.ayush.userdesk.domain.dto;

import lombok.Builder;

@Builder
public record LoginResponseDto(
        String token,
        Long expiresInSeconds
) {
}

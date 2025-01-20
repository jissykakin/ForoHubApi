package com.foro.hubApi.domain.users.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank
        String username,
        @NotBlank
        String password) {
}

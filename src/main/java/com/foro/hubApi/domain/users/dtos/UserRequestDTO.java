package com.foro.hubApi.domain.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        @Email
        String email,
        @NotNull
        @Pattern(regexp = "^(ROLE_USER|ROLE_ADMIN)$")
        String role,
        String avatar,
        String profession
) {
}

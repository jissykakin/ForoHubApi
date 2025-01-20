package com.foro.hubApi.domain.users.dtos;

import com.foro.hubApi.domain.roles.RoleName;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String avatar,
        String profession,
        RoleName role
) {
}

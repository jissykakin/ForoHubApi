package com.foro.hubApi.domain.users.validators;

import com.foro.hubApi.domain.users.dtos.UserRequestDTO;
import jakarta.validation.Valid;

public interface ValidatorsUsers {
    void validatorUser(@Valid UserRequestDTO user, Long id);
}

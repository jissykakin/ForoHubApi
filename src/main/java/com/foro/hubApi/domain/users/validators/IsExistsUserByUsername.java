package com.foro.hubApi.domain.users.validators;

import com.foro.hubApi.domain.users.UserRepository;
import com.foro.hubApi.domain.users.dtos.UserRequestDTO;
import com.foro.hubApi.exception.ExceptionValidation;
import com.foro.hubApi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IsExistsUserByUsername implements ValidatorsUsers {

    @Autowired
    UserRepository userRepository;

    @Override
    public void validatorUser(UserRequestDTO user, Long id) {
        if (user.username() == null) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser null");
        }
        boolean isExistUserNameByEmail = userRepository.existsByUsernameAndStatusTrue(user.username());
        if (isExistUserNameByEmail) {
            throw new ExceptionValidation("El nombre de usuario ya Existe");
        }
    }
}

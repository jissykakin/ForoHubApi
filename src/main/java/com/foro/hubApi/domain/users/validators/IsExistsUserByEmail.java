package com.foro.hubApi.domain.users.validators;

import com.foro.hubApi.domain.users.UserRepository;
import com.foro.hubApi.domain.users.dtos.UserRequestDTO;
import com.foro.hubApi.exception.ExceptionValidation;
import com.foro.hubApi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IsExistsUserByEmail implements ValidatorsUsers{

    @Autowired
    UserRepository userRepository;

    @Override
    public void validatorUser(UserRequestDTO user, Long id) {
        if (user.email() == null) {
            throw new IllegalArgumentException("Emailno puede ser null");
        }
        var isExistUserByEmail = userRepository.existsByEmailAndStatusTrue(user.email());
        if (isExistUserByEmail) {
            throw new ExceptionValidation("El Email ya Existe");
        }
    }
}

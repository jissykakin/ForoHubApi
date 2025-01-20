package com.foro.hubApi.domain.users;

import com.foro.hubApi.domain.roles.Role;
import com.foro.hubApi.domain.roles.RoleName;
import com.foro.hubApi.domain.roles.RoleRepository;
import com.foro.hubApi.domain.topics.validations.ValidatorsTopics;
import com.foro.hubApi.domain.users.dtos.UserRequestDTO;
import com.foro.hubApi.domain.users.validators.ValidatorsUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private List<ValidatorsUsers> validatorsUsersList;

    public User saveUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO);

        validatorsUsersList.forEach(v -> v.validatorUser(userRequestDTO, null));
//
        String encodedPassword = passwordEncoder.encode(userRequestDTO.password());
        user.setPassword(encodedPassword);

        Role role = roleRepository.findByName(RoleName.valueOf(userRequestDTO.role()));
        if (role == null) {
            throw new IllegalArgumentException("Invalid role");
        }

        user.setRole(role);
        return userRepository.save(user);
    }
}

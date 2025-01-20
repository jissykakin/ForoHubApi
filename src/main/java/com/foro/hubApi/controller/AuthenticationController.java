package com.foro.hubApi.controller;

import com.foro.hubApi.domain.users.User;
import com.foro.hubApi.domain.users.UserService;
import com.foro.hubApi.domain.users.dtos.LoginDTO;
import com.foro.hubApi.domain.users.dtos.UserRequestDTO;
import com.foro.hubApi.domain.users.dtos.UserResponseDTO;
import com.foro.hubApi.infra.security.JwtTokenDTO;
import com.foro.hubApi.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody @Valid LoginDTO loginDTO) {

        Authentication authToken = new UsernamePasswordAuthenticationToken(loginDTO.username(),
                loginDTO.password());
        System.out.println(authToken);
        var userAuthenticate = authenticationManager.authenticate(authToken);
        System.out.println(userAuthenticate);
        var JWTtoken = tokenService.generarToken((User) userAuthenticate.getPrincipal());
        return ResponseEntity.ok(new JwtTokenDTO(JWTtoken));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRequestDTO registerUser , UriComponentsBuilder uriComponentsBuilder) {

        User user = userService.saveUser(registerUser);
        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getAvatar(), user.getProfession(), user.getRole().getName());
        URI url = uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(url).body(userResponseDTO);

    }

}

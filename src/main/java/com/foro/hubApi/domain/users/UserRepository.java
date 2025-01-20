package com.foro.hubApi.domain.users;

import com.foro.hubApi.domain.topics.TopicRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
    UserDetails findByUsernameOrEmail(String usernameOrEmail);


    boolean existsByEmailAndStatusTrue(@NotBlank @Email String email);

    boolean existsByUsernameAndStatusTrue(@NotBlank String username);
}

package com.foro.hubApi.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.foro.hubApi.domain.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiKeySecret;

    public String generarToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiKeySecret);
            return JWT.create()
                    .withIssuer("Foro_Hub")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }


    public String verifyToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiKeySecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Foro_Hub")
                    .build();
            return verifier.verify(token).getSubject();
        } catch (JWTDecodeException exception) {
            throw new JWTDecodeException("Invalid token format: " + exception.getMessage(), exception);
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Invalid token: " + exception.getMessage(), exception);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error creating JWT: " + exception.getMessage(), exception);
        }
    }


    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}

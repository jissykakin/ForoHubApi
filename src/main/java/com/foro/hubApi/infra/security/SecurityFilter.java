package com.foro.hubApi.infra.security;

import com.foro.hubApi.domain.users.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var authToken =request.getHeader("Authorization");
            if (authToken !=null){
                var token = authToken.replace("Bearer ", "");
                var nameUser = tokenService.verifyToken(token);
                System.out.println(nameUser);
                if (nameUser != null){
                    var user = userRepository.findByUsernameOrEmail(nameUser);
                    var authentication = new UsernamePasswordAuthenticationToken(user, null,
                            user.getAuthorities()); // Forzamos un inicio de sesion
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
            return;
        }

        filterChain.doFilter(request, response);

    }
}

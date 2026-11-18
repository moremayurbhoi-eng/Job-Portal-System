package com.jobportal.service;

import com.jobportal.dto.Dtos.*;
import com.jobportal.model.User;
import com.jobportal.repository.UserRepository;
import com.jobportal.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public AuthResponse register(RegisterRequest req) {
        String email = req.email().trim().toLowerCase();
        if (users.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with this email already exists");
        }
        User nu = new User();
        nu.setName(req.name().trim());
        nu.setEmail(email);
        nu.setPassword(encoder.encode(req.password()));
        nu.setRole(req.role());
        nu.setCompany(req.company());
        User u = users.save(nu);
        return new AuthResponse(jwt.generate(u.getEmail(), u.getRole().name()), toDto(u));
    }

    public AuthResponse login(LoginRequest req) {
        User u = users.findByEmail(req.email().trim().toLowerCase())
                .filter(x -> encoder.matches(req.password(), x.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        return new AuthResponse(jwt.generate(u.getEmail(), u.getRole().name()), toDto(u));
    }

    public static UserDto toDto(User u) {
        return new UserDto(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.getCompany());
    }
}

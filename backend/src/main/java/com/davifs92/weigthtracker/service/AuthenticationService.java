package com.davifs92.weigthtracker.service;

import com.davifs92.weigthtracker.config.JWTService;
import com.davifs92.weigthtracker.dto.AuthenticationRequest;
import com.davifs92.weigthtracker.dto.AuthenticationResponse;
import com.davifs92.weigthtracker.dto.RegisterRequest;
import com.davifs92.weigthtracker.entities.Role;
import com.davifs92.weigthtracker.entities.User;
import com.davifs92.weigthtracker.repository.UserRepository;
import com.davifs92.weigthtracker.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .age(request.getAge())
                .age(request.getAge())
                .goal(request.getGoal())
                .height(request.getHeight())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

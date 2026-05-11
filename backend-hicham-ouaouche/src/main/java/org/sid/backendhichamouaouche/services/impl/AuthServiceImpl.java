package org.sid.backendhichamouaouche.services.impl;

import org.sid.backendhichamouaouche.dtos.AuthRequest;
import org.sid.backendhichamouaouche.dtos.AuthResponse;
import org.sid.backendhichamouaouche.dtos.RegisterRequest;
import org.sid.backendhichamouaouche.entities.AppUser;
import org.sid.backendhichamouaouche.exceptions.BusinessException;
import org.sid.backendhichamouaouche.repositories.UserRepository;
import org.sid.backendhichamouaouche.security.JwtService;
import org.sid.backendhichamouaouche.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        AppUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("Invalid credentials"));
        return buildResponse(user);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Username already exists");
        }
        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();
        AppUser saved = userRepository.save(user);
        return buildResponse(saved);
    }

    private AuthResponse buildResponse(AppUser user) {
        var userDetails = User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name().replace("ROLE_", ""))
                .build();
        return AuthResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .token(jwtService.generateToken(userDetails))
                .build();
    }
}
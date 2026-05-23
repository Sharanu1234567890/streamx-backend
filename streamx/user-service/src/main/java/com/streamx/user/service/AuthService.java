package com.streamx.user.service;

import com.streamx.user.dto.UserDtos.*;
import com.streamx.user.exception.EmailAlreadyExistsException;
import com.streamx.user.exception.InvalidCredentialsException;
import com.streamx.user.model.User;
import com.streamx.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.USER)
                .enabled(true)
                .build();

        User saved = userRepository.save(user);
        log.info("New user registered: {}", saved.getEmail());

        return buildAuthResponse(saved);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!user.isEnabled()) {
            throw new InvalidCredentialsException("Account is disabled");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        log.info("User logged in: {}", user.getEmail());
        return buildAuthResponse(user);
    }

    public AuthResponse refreshToken(RefreshRequest request) {
        String token = request.getRefreshToken();

        if (!jwtService.isTokenValid(token)) {
            throw new InvalidCredentialsException("Refresh token is expired or invalid");
        }

        String userId = jwtService.extractUserId(token);
        User user = userRepository.findById(java.util.UUID.fromString(userId))
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        return buildAuthResponse(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        String userId = user.getId().toString();
        String accessToken = jwtService.generateAccessToken(userId, user.getEmail(), user.getRole().name());
        String refreshToken = jwtService.generateRefreshToken(userId);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setFullName(user.getFullName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole().name());

        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(jwtService.getAccessTokenExpiry());
        response.setUser(userResponse);

        return response;
    }
}

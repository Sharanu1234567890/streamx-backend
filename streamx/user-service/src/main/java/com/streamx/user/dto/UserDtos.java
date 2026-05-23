package com.streamx.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserDtos {

    @Data
    public static class RegisterRequest {
        @NotBlank(message = "Full name is required")
        private String fullName;

        @Email(message = "Must be a valid email")
        @NotBlank(message = "Email is required")
        private String email;

        @Size(min = 8, message = "Password must be at least 8 characters")
        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    public static class LoginRequest {
        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Data
    public static class AuthResponse {
        private String accessToken;
        private String refreshToken;
        private String tokenType = "Bearer";
        private long expiresIn;       // seconds
        private UserResponse user;
    }

    @Data
    public static class UserResponse {
        private String id;
        private String fullName;
        private String email;
        private String role;
    }

    @Data
    public static class RefreshRequest {
        @NotBlank
        private String refreshToken;
    }
}

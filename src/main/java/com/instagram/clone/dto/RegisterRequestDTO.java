package com.instagram.clone.dto;

public record RegisterRequestDTO(String username, String email, String password, String fullName, String bio,
        String profilePicture) {
}
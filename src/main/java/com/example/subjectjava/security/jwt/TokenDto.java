package com.example.subjectjava.security.jwt;

public record TokenDto(
        String accessToken,
        String refreshToken
) {
}

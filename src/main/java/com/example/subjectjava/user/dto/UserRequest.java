package com.example.subjectjava.user.dto;

public record UserRequest(
        String username,
        String password,
        String nickname
) {
}

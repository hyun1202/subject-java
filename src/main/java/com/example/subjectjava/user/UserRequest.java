package com.example.subjectjava.user;

public record UserRequest(
        String username,
        String password,
        String nickname
) {
}

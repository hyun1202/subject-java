package com.example.subjectjava.user;

import java.util.List;

public record UserResponse(
        String username,
        String nickname,
        List<String> authorities
) {
}

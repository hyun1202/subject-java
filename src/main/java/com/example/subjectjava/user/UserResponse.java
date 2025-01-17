package com.example.subjectjava.user;

import java.util.List;

public record UserResponse(
        String username,
        String nickname,
        List<String> authorities
) {
    public static UserResponse of(User user) {
        return new UserResponse(user.getUsername(),
                user.getNickname(),
                user.getAuthorities()
        );
    }
}

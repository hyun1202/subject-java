package com.example.subjectjava.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nickname;

    private String refreshToken;
    private String authorities;

    public List<String> getAuthorities() {
        return Arrays.stream(authorities.split(",")).toList();
    }

    public void updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

package com.example.subjectjava.user;

import com.example.subjectjava.security.jwt.TokenDto;
import com.example.subjectjava.security.jwt.TokenProvider;
import com.example.subjectjava.user.dto.SignRequest;
import com.example.subjectjava.user.dto.UserRequest;
import com.example.subjectjava.user.dto.UserResponse;
import com.example.subjectjava.user.dto.UserSignResponse;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private UserService userService;

    String username = "test";
    String password = "password";
    String nickname = "nickname";
    String role = "ROLE_USER";
    String encodedPassword = "encodedPassword";

    @Test
    void signup() {
        UserRequest req = new UserRequest(username, password, nickname);
        when(userRepository.save(any())).thenReturn(getUser());

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        UserResponse res = userService.signup(req);

        Assertions.assertEquals(username, res.username());
        Assertions.assertEquals(nickname, res.nickname());
        Assertions.assertEquals(role, res.authorities().get(0));
    }

    @Test
    void sign() {
        SignRequest req = new SignRequest(username, password);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(getUser()));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(tokenProvider.createTokenDto(any(), any())).thenReturn(new TokenDto("accessToken", "refreshToken"));
        UserSignResponse token = userService.sign(req);

        Assertions.assertNotNull(token);
    }

    private User getUser() {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .authorities(role)
                .build();
    }
}
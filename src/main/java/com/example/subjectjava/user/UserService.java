package com.example.subjectjava.user;

import com.example.subjectjava.security.jwt.TokenDto;
import com.example.subjectjava.security.jwt.TokenProvider;
import com.example.subjectjava.user.dto.SignRequest;
import com.example.subjectjava.user.dto.UserRequest;
import com.example.subjectjava.user.dto.UserResponse;
import com.example.subjectjava.user.dto.UserSignResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public UserResponse signup(UserRequest req) {
        User user = User.builder()
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .nickname(req.nickname())
                .authorities("ROLE_USER")
                .build();

        user = userRepository.save(user);

        return UserResponse.of(user);
    }

    @Transactional
    public UserSignResponse sign(SignRequest req) {

        User user = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new IllegalArgumentException(req.username() + "은 존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        TokenDto tokenDto = tokenProvider.createTokenDto(user.getUsername(), user.getAuthorities());

        user.updateToken(tokenDto.refreshToken());

        return new UserSignResponse(tokenDto.refreshToken());
    }
}

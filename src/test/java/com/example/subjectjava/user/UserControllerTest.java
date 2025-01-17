package com.example.subjectjava.user;

import com.example.subjectjava.user.dto.SignRequest;
import com.example.subjectjava.user.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입")
    @Test
    @Transactional
    void signup() throws Exception {
        String username = "test2";
        String nickname = "test2_nickname";
        String role = "ROLE_USER";

        UserRequest req = new UserRequest(username, "password", nickname);

        String content = objectMapper.writeValueAsString(req);

        mockMvc.perform(post("/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.authorities[0]").value(role))
                .andDo(print());
    }

    @DisplayName("로그인")
    @Test
    @Transactional
    void sign() throws Exception {
        String username = "test";
        String password = "password";

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname("nickname")
                .authorities("ROLE_USER")
                .build();

        userRepository.save(user);

        SignRequest req = new SignRequest(username, password);

        String content = objectMapper.writeValueAsString(req);

        mockMvc.perform(post("/sign")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").isString())
                .andDo(print());
    }
}
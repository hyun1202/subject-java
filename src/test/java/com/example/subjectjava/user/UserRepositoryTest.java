package com.example.subjectjava.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUsername() {
        String username = "test12";

        User user = User.builder()
                .username(username)
                .build();

        userRepository.save(user);

        user = userRepository.findByUsername(username).orElse(new User());

        Assertions.assertEquals(username, user.getUsername());
    }

}
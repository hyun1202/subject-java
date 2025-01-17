package com.example.subjectjava.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse signup(UserRequest req) {
        return null;
    }

    public UserSignResponse sign(UserRequest req) {
        return null;
    }
}

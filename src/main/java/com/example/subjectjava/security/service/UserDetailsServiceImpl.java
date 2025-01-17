package com.example.subjectjava.security.service;

import com.example.subjectjava.user.User;
import com.example.subjectjava.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::createUser)
                .orElseThrow(() -> new IllegalArgumentException(username + "은 존재하지 않는 사용자입니다."));
    }

    private UserDetails createUser(User user){
        return new UserDetailsImpl(user);
    }
}


package com.example.subjectjava.user;

import com.example.subjectjava.user.dto.SignRequest;
import com.example.subjectjava.user.dto.UserRequest;
import com.example.subjectjava.user.dto.UserResponse;
import com.example.subjectjava.user.dto.UserSignResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserRequest req) {
        return ResponseEntity.ok(userService.signup(req));
    }

    @PostMapping("/sign")
    public ResponseEntity<UserSignResponse> sign(@RequestBody SignRequest req) {
        return ResponseEntity.ok(userService.sign(req));
    }
}

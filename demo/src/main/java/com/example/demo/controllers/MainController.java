package com.example.demo.controllers;

import com.example.demo.dto.CreateUserDto;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return userService.helloUser();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody CreateUserDto dto) throws Exception {
        return userService.createUser(dto);
    }

    @GetMapping("/users/id/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }
    
    @PutMapping("/users/id/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody CreateUserDto dto, @PathVariable Long userId) {
        User user = userService.updateUser(dto, userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping("/users/id/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/email/{userEmail}")
    public ResponseEntity<User> getUserEmail(@PathVariable String userEmail) {
        User user = userService.getUserEmail(userEmail);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @ExceptionHandler
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        String message = "Параметры не соответствуют ожидаемым: " + e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

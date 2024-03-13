package com.example.demo.services;

import com.example.demo.dto.CreateUserDto;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.regex.Pattern;

@Validated
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String helloUser() {
        return "Hello, everyone!";
    }

    private boolean EmailValidator(String email) {
        String reg = "\\S+@\\S+\\.\\S+";
        Pattern pattern = Pattern.compile(reg);
        return email.matches(reg);
    }


    public User createUser(@Valid CreateUserDto dto) throws Exception {
        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMiddleName(dto.getMiddleName());
        user.setEmail(dto.getEmail());
        user.setDateOfBirth(dto.getDateOfBirth());

        String bcryptHashString = BCrypt.withDefaults().hashToString(12, dto.getPassword().toCharArray());
        user.setPassword(bcryptHashString);

        String email = user.getEmail();
        User existingEmailUser = userRepository.findByEmail(email).orElse(null);
        if (existingEmailUser != null) {
            throw new IllegalArgumentException("Пользователь с email " + email + " уже существует");
        }
        return userRepository.save(user);
    }


    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateUser(CreateUserDto dto, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMiddleName(dto.getMiddleName());
        user.setEmail(dto.getEmail());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setPassword(dto.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserEmail(String userEmail) { return userRepository.findByEmail(userEmail).orElse(null); }
}
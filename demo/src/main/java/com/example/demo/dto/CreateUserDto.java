package com.example.demo.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Max;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CreateUserDto {
    @NotBlank(message = "Отсутствует имя пользователя")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Отсутствует фамилия пользователя")
    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String middleName;

    @NotBlank(message = "Отсутствует email пользователя")
    @Email(message = "Неверный формат email")
    @Size(max = 25)
    private String email;

    @Past
    private LocalDateTime dateOfBirth;

    @NotBlank(message = "Отсутствует пароль пользователя")
    private String password;
}

package ru.danchenski.TaskManagementSystem.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.danchenski.TaskManagementSystem.entities.CommentEntity;
import ru.danchenski.TaskManagementSystem.entities.TaskEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Никнейм не должен быть пустым!")
    private String username;

    @Email
//    @NotEmpty(message = "Email не должен быть пустым!")
    private String email;

    @NotEmpty(message = "Пароль не должен быть пустым!")
    private String password;
}

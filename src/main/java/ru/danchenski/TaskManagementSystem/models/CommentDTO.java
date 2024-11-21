package ru.danchenski.TaskManagementSystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.danchenski.TaskManagementSystem.entities.TaskEntity;
import ru.danchenski.TaskManagementSystem.entities.UserEntity;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    @NotEmpty(message = "Комментарий не должен быть пустым!")
    @Size(min = 1, max = 255, message = "Комментарий должен быть от 1 до 255 символов!")
    private String content;

    private UserDTO author;

    private TaskDTO task;

}

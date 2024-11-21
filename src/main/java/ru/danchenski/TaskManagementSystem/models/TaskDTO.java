package ru.danchenski.TaskManagementSystem.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.danchenski.TaskManagementSystem.entities.CommentEntity;
import ru.danchenski.TaskManagementSystem.entities.Priority;
import ru.danchenski.TaskManagementSystem.entities.Status;
import ru.danchenski.TaskManagementSystem.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class TaskDTO {

    private Long id;

    @NotEmpty(message = "Заголовок не должен быть пустым!")
    @Size(min = 1, max = 30, message = "Заголовок должен быть от 1 до 30 символов!")
    private String title;

    @NotEmpty(message = "Описание не должно быть пустым!")
    @Size(min = 1, max = 100, message = "Описание должно быть от 1 до 100 символов!")
    private String description;

    @NotNull(message = "Статус не должен быть пустым!")
    private Status status;

    @NotNull(message = "Приоритет не должен быть пустым!")
    private Priority priority;

    @NotNull(message = "Автор не должен быть пустым!")
    private UserDTO author;

    private UserDTO executor;
}

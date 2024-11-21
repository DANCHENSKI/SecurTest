package ru.danchenski.TaskManagementSystem.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "tasks", schema = "taskmanagementsystem")
public class TaskEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Заголовок не должен быть пустым!")
    @Size(min = 1, max = 30, message = "Заголовок должен быть от 1 до 30 символов!")
    private String title;

    @Column(name = "description")
    @NotEmpty(message = "Описание не должно быть пустым!")
    @Size(min = 1, max = 100, message = "Описание должно быть от 1 до 100 символов!")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Статус не должен быть пустым!")
    private Status status;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Приоритет не должен быть пустым!")
    private Priority priority;

    @ManyToOne
    @NotNull(message = "Автор не должен быть пустым!")
    @JoinColumn(name = "author", referencedColumnName = "id", nullable = false)
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "executor", referencedColumnName = "id")
    private UserEntity executor ;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

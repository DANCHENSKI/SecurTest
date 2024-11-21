package ru.danchenski.TaskManagementSystem.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.danchenski.TaskManagementSystem.entities.TaskEntity;
import ru.danchenski.TaskManagementSystem.entities.UserEntity;
import ru.danchenski.TaskManagementSystem.exceptions.TaskException;
import ru.danchenski.TaskManagementSystem.mappers.TaskMapper;
import ru.danchenski.TaskManagementSystem.models.TaskDTO;
import ru.danchenski.TaskManagementSystem.repositories.TaskRepository;
import ru.danchenski.TaskManagementSystem.utils.TaskErrorsUtil;
import ru.danchenski.TaskManagementSystem.utils.TaskErrorResponse;
import ru.danchenski.TaskManagementSystem.validators.TaskValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskValidator taskValidator;
    private final UserService userService;

    public ResponseEntity<HttpStatus> add(@RequestBody @Valid TaskDTO taskDTO,
                                          BindingResult bindingResult) {
        taskValidator.validate(taskDTO, bindingResult);
        if (bindingResult.hasErrors())
            TaskErrorsUtil.returnErrorsToClient(bindingResult);
        TaskEntity taskEntity = taskMapper.toTaskEntity(taskDTO);

        UserEntity author = userService.findById(taskEntity.getAuthor().getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        // потом поменять

        taskEntity.setAuthor(author);
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskRepository.save(taskEntity);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public Optional<TaskEntity> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<TaskEntity> getTasks() {
        return taskRepository.findAll();
    }

    public ResponseEntity<HttpStatus> updateById(@RequestBody @Valid TaskDTO taskDTO,
                                                 Long id, BindingResult bindingResult) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskValidator.validate(taskDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        TaskEntity taskEntity = taskMapper.toTaskEntity(taskDTO);
        taskEntity.setId(id); // Устанавливаем ID для обновления
        taskEntity.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(taskEntity);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {

        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    } // вынос в отдельный метод создание и обновления.

    @ExceptionHandler
    private ResponseEntity<TaskErrorResponse> handleException(TaskException e) {
        TaskErrorResponse  response = new TaskErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}

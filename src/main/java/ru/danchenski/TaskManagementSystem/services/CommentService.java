package ru.danchenski.TaskManagementSystem.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.danchenski.TaskManagementSystem.entities.CommentEntity;
import ru.danchenski.TaskManagementSystem.entities.TaskEntity;
import ru.danchenski.TaskManagementSystem.entities.UserEntity;
import ru.danchenski.TaskManagementSystem.exceptions.CommentException;
import ru.danchenski.TaskManagementSystem.mappers.CommentMapper;
import ru.danchenski.TaskManagementSystem.models.CommentDTO;
import ru.danchenski.TaskManagementSystem.repositories.CommentRepository;
import ru.danchenski.TaskManagementSystem.utils.CommentErrorResponse;
import ru.danchenski.TaskManagementSystem.utils.TaskErrorsUtil;
import ru.danchenski.TaskManagementSystem.validators.CommentValidator;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final CommentValidator commentValidator;
    private final UserService userService;
    private final TaskService taskService;

    public ResponseEntity<HttpStatus> add(CommentDTO commentDTO,
                                          BindingResult bindingResult) {
        commentValidator.validate(commentDTO, bindingResult);
        if (bindingResult.hasErrors())
            TaskErrorsUtil.returnErrorsToClient(bindingResult);

        CommentEntity commentEntity = commentMapper.toCommentEntity(commentDTO);

        UserEntity author = userService.findById(commentEntity.getAuthor().getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        TaskEntity task = taskService.findById(commentEntity.getTask().getId())
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена"));
        commentEntity.setCreatedAt(LocalDateTime.now());
        commentEntity.setAuthor(author);
        commentEntity.setTask(task);
        commentRepository.save(commentEntity);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public List<CommentEntity> getComments() {
        return commentRepository.findAll(); // пересмотри позже этот метод
    }

    public ResponseEntity<HttpStatus> updateById(CommentDTO commentDTO,
                                                 Long id, BindingResult bindingResult) {
        if (!commentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        commentValidator.validate(commentDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        CommentEntity commentEntity = commentMapper.toCommentEntity(commentDTO);
        commentEntity.setId(id); // Устанавливаем ID для обновления
        commentEntity.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(commentEntity);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        if (!commentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        commentRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteAll() {
        commentRepository.deleteAll();

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<CommentErrorResponse> handleException(CommentException e) {
        CommentErrorResponse  response = new CommentErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}

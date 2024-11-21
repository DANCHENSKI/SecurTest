package ru.danchenski.TaskManagementSystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.danchenski.TaskManagementSystem.entities.CommentEntity;
import ru.danchenski.TaskManagementSystem.models.CommentDTO;
import ru.danchenski.TaskManagementSystem.services.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid CommentDTO commentDTO,
                                          BindingResult bindingResult) {
        return commentService.add(commentDTO, bindingResult);
    }

    @GetMapping()
    private List<CommentEntity> getComments() {
        return commentService.getComments();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateById(@RequestBody @Valid CommentDTO commentDTO,
                                                 @PathVariable("id") Long id, BindingResult bindingResult) {
        return commentService.updateById(commentDTO, id, bindingResult);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        return commentService.deleteById(id);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<HttpStatus> deleteAll() {
        return commentService.deleteAll();
    }
}

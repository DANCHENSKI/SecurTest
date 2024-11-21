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
import ru.danchenski.TaskManagementSystem.entities.TaskEntity;
import ru.danchenski.TaskManagementSystem.models.TaskDTO;
import ru.danchenski.TaskManagementSystem.services.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid TaskDTO taskDTO,
                                          BindingResult bindingResult) {
        return taskService.add(taskDTO, bindingResult);
    }

    @GetMapping
    public List<TaskEntity> getTasks() {
        return taskService.getTasks();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateById(@RequestBody @Valid TaskDTO taskDTO,
                                                 @PathVariable("id") Long id, BindingResult bindingResult) {
        return taskService.updateById(taskDTO, id, bindingResult);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        return taskService.deleteById(id);
    }
}

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
import ru.danchenski.TaskManagementSystem.entities.UserEntity;
import ru.danchenski.TaskManagementSystem.models.UserDTO;
import ru.danchenski.TaskManagementSystem.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid UserDTO userDTO,
                                          BindingResult bindingResult) { // поменять местами потом get-post-put-delete
        return userService.add(userDTO, bindingResult);
    }

    @GetMapping
    public List<UserEntity> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateById(@RequestBody @Valid UserDTO userDTO,
                                                 @PathVariable("id") Long id, BindingResult bindingResult) {
        return userService.updateById(userDTO, id, bindingResult);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        return userService.deleteById(id);
    }

}

package ru.danchenski.TaskManagementSystem.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.danchenski.TaskManagementSystem.entities.UserEntity;
import ru.danchenski.TaskManagementSystem.exceptions.TaskException;
import ru.danchenski.TaskManagementSystem.exceptions.UserException;
import ru.danchenski.TaskManagementSystem.mappers.UserMapper;
import ru.danchenski.TaskManagementSystem.models.UserDTO;
import ru.danchenski.TaskManagementSystem.repositories.UserRepository;
import ru.danchenski.TaskManagementSystem.securities.PersonDetails;
import ru.danchenski.TaskManagementSystem.utils.TaskErrorResponse;
import ru.danchenski.TaskManagementSystem.utils.TaskErrorsUtil;
import ru.danchenski.TaskManagementSystem.utils.UserErrorResponse;
import ru.danchenski.TaskManagementSystem.validators.UserValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    public ResponseEntity<HttpStatus> add(@Valid UserDTO userDTO,
                                          BindingResult bindingResult) {
        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors())
            TaskErrorsUtil.returnErrorsToClient(bindingResult);
        UserEntity userEntity = userMapper.toUserEntity(userDTO);
        userEntity.setCreatedAt(LocalDateTime.now());
        userRepository.save(userEntity);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<HttpStatus> updateById(@RequestBody @Valid UserDTO userDTO, Long id,
                                                 BindingResult bindingResult) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userMapper.toUserEntity(userDTO);
        userEntity.setId(id); // Устанавливаем ID для обновления
        userEntity.setUpdatedAt(LocalDateTime.now());
        userRepository.save(userEntity);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserException e) {
        UserErrorResponse  response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> person = userRepository.findByUsername(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }
}

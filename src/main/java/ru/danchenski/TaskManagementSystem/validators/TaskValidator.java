package ru.danchenski.TaskManagementSystem.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.danchenski.TaskManagementSystem.entities.TaskEntity;
import ru.danchenski.TaskManagementSystem.models.TaskDTO;
import ru.danchenski.TaskManagementSystem.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class TaskValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return TaskDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TaskDTO task = (TaskDTO) target;

        if (userRepository.findById(task.getAuthor().getId()).isEmpty())
            errors.rejectValue("author", "Нет зарегистрированного пользователя с таким именем!");
    }
}

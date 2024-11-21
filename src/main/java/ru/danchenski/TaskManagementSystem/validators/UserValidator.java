package ru.danchenski.TaskManagementSystem.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.danchenski.TaskManagementSystem.entities.UserEntity;
import ru.danchenski.TaskManagementSystem.models.UserDTO;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO user = (UserDTO) target;

        // Проверка email
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "field.required", "Email не должен быть пустым!");
        } else if (!isValidEmail(user.getEmail())) {
            errors.rejectValue("email", "field.invalid", "Некорректный формат email.");
        }

        // Проверка пароля
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            errors.rejectValue("password", "field.invalid", "Пароль должен содержать не менее 6 символов.");
        }
    }
        private boolean isValidEmail(String email) {
            // Простейшая проверка формата email, можно использовать более сложные регулярные выражения.
            return email.contains("@") && email.contains(".");
            // Обнови это позже
    }
}

package ru.danchenski.TaskManagementSystem.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.danchenski.TaskManagementSystem.exceptions.TaskException;
import ru.danchenski.TaskManagementSystem.exceptions.UserException;

import java.util.List;

public class UserErrorUtil {
    public static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append(";");
        }

        throw new UserException(errorMsg.toString());
    }
}


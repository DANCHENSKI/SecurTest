package ru.danchenski.TaskManagementSystem.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.danchenski.TaskManagementSystem.entities.CommentEntity;
import ru.danchenski.TaskManagementSystem.models.CommentDTO;

@Component
@RequiredArgsConstructor
public class CommentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CommentDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CommentDTO comment = (CommentDTO) target;

        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            errors.rejectValue("text", "field.required", "Comment text is required.");
        }

        if (comment.getAuthor() == null) {
            errors.rejectValue("author", "field.required", "Author ID is required.");
        }
    }
}

package ru.danchenski.TaskManagementSystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.danchenski.TaskManagementSystem.entities.UserEntity;
import ru.danchenski.TaskManagementSystem.mappers.UserMapper;
import ru.danchenski.TaskManagementSystem.models.UserDTO;
import ru.danchenski.TaskManagementSystem.services.RegistrationService;
import ru.danchenski.TaskManagementSystem.validators.UserValidator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final UserMapper userMapper;

    @GetMapping("/auth/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") UserDTO userDTO) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid UserDTO userDTO,
                                      BindingResult bindingResult) {
        userValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";

        UserEntity userEntity = userMapper.toUserEntity(userDTO);
        registrationService.register(userEntity);

        return "redirect:/auth/login";
    }
}

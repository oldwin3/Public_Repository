package ru.itis.orisjavaproject.Controllers.RegAndAuthControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.orisjavaproject.Entities.form.SignUpForm;
import ru.itis.orisjavaproject.Services.SignUpService.SignUpService;


@Controller
@Slf4j
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        log.info("GET request received for /signUp");

        return "sign_up";
    }

    @PostMapping("/signUp")
    public String signUpUser(SignUpForm signUpForm) {
        log.info("POST request received for /signUp with form data: {}", signUpForm);
        signUpService.signUp(signUpForm);
        log.info("User signed up successfully");
        return "redirect:/signIn";
    }
}
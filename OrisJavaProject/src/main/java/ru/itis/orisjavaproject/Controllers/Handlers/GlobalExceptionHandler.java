
package ru.itis.orisjavaproject.Controllers.Handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.orisjavaproject.Services.ImageService;
import ru.itis.orisjavaproject.exception.ServiceException;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final ImageService imageService;

    @ExceptionHandler(ServiceException.class)
    public String handleServiceException(ServiceException exception, Model model) {
        log.warn("Handling ServiceException: {}", exception.getMessage(), exception);
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        model.addAttribute("errorMessage", exception.getMessage());
        return "erorr";
    }

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception exception, Model model) {
        log.warn("Handling general Exception: {}", exception.getMessage(), exception);
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        model.addAttribute("errorMessage", "Произошла ошибка. Пожалуйста, попробуйте еще раз.");
        return "erorr";
    }
}
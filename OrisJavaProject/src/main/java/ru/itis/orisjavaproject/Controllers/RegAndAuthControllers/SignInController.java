package ru.itis.orisjavaproject.Controllers.RegAndAuthControllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.orisjavaproject.Services.ImageService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SignInController {

    @GetMapping("/signIn")
    public String getSignInPage(HttpServletRequest request, ModelMap model) {
        if (request.getParameterMap().containsKey("error")) {
            log.info("Error parameter detected in sign-in request");
            model.addAttribute("error", "error");
        }

        log.info("GET request received for /signIn");
        return "sign_in";
    }
}

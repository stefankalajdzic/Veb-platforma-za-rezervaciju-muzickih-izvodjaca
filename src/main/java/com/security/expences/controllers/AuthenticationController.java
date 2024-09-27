package com.security.expences.controllers;

import com.security.expences.dto.ErrorDTO;
import com.security.expences.dto.LoginRequestDTO;
import com.security.expences.dto.RegistrationRequestDTO;
import com.security.expences.model.User;
import com.security.expences.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller()
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    UserService userService;

    //https://bootify.io/spring-security/form-login-with-spring-boot-thymeleaf.html
    @GetMapping("/login")
    public String login(@RequestParam(required = false) final Boolean loginRequired,
                        @RequestParam(required = false) final Boolean loginError,
                        @RequestParam(required = false) final Boolean logoutSuccess, final Model model) {
        model.addAttribute("authentication", new LoginRequestDTO());
        if (loginRequired == Boolean.TRUE) {
            model.addAttribute("error", "Please login");
        }
        if (loginError == Boolean.TRUE) {
            model.addAttribute("error", "Incorrect credentials");
        }
        if (logoutSuccess == Boolean.TRUE) {
            model.addAttribute("warning", "You're logged out successfully");
        }
        return "authentication/loginPage";
    }

    @GetMapping("/registration")
    public ModelAndView registration(@RequestParam(required = false, name = "error") String error) {
        ModelAndView modelAndView = new ModelAndView("authentication/registrationPage");
        modelAndView.addObject("registrationRequest", new RegistrationRequestDTO());
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @PostMapping("/registration")
    public RedirectView registerUser(@ModelAttribute @Validated RegistrationRequestDTO registrationRequest) { //, HttpServletResponse response
        userService.registerUserByNameAndUsername(registrationRequest);
        return new RedirectView("/authentication/login", true);
    }
}

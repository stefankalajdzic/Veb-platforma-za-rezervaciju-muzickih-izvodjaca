package com.security.expences.controllers;

import com.security.expences.util.MusicException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    private @Autowired HttpServletRequest request;


    @ExceptionHandler(AccessDeniedException.class)
    public RedirectView handleAccesDeniedException(AccessDeniedException exception) {
        return new RedirectView("/login", true);
    }



    @ExceptionHandler(MusicException.class)
    public RedirectView handleExpencesException(MusicException exception) {
        String path = this.request.getRequestURL().toString().split("app/")[1];
        if(path.equals("reservation/reservation")){
            path = "dashboard/list";
        }
        RedirectView errorPage = new RedirectView("/" + path, true);
        errorPage.getAttributesMap().put("error", exception.getMessage());
        return errorPage;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RedirectView handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        HashMap<String, List<String>> validationErrors = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            if (validationErrors.get(error.getField()) == null) {
                List<String> validationErrorMessages = new ArrayList<>();
                validationErrorMessages.add(error.getDefaultMessage());
                validationErrors.put(error.getField(), validationErrorMessages);
                continue;
            }

            validationErrors.get(error.getField()).add(error.getDefaultMessage());
        }
        String path = this.request.getRequestURL().toString().split("app/")[1];
        if(path.equals("reservation/reservation")){
            path = "dashboard/list";
        }
        RedirectView errorPage = new RedirectView("/" + path, true);

        sb.append("Incorrect fields: ");
        for(Map.Entry<String, List<String>> error:validationErrors.entrySet()){
            sb.append(splitCamelCase(error.getKey()).toLowerCase()).append(" ");
        }

        errorPage.getAttributesMap().put("error", sb);

        return errorPage;
    }

    @ExceptionHandler(Exception.class)
    public RedirectView handleException(Exception exception) {
        String path = this.request.getRequestURL().toString().split("app/")[1];
        if(path.equals("reservation/reservation")){
            path = "dashboard/list";
        }
        RedirectView errorPage = new RedirectView("/" + path, true);
        errorPage.getAttributesMap().put("error", exception.getMessage());
        return errorPage;
    }

    static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}

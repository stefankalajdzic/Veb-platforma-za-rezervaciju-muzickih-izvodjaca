package com.security.expences.controllers;

import com.security.expences.dto.ReservationDTO;
import com.security.expences.model.Reservation;
import com.security.expences.model.User;
import com.security.expences.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller()
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/me")
    public ModelAndView userPage(@RequestParam(required = false, name = "error") String error, @RequestParam(required = false, name = "filter") String filter) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("user/userPage");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findOneByUserName(userDetails.getUsername());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("isBand", !currentUser.getBandName().isEmpty());
        modelAndView.addObject("message", "");
        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute @Validated User user) throws ParseException {
        userService.updateUser(user);
        ModelAndView modelAndView = new ModelAndView("user/userPage");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findOneByUserName(userDetails.getUsername());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("isBand", !currentUser.getBandName().isEmpty());
        modelAndView.addObject("message", "Profile updated successfully");
        return modelAndView;
    }
}

package com.security.expences.controllers;

import com.security.expences.dto.MediaDTO;
import com.security.expences.model.Media;
import com.security.expences.model.User;
import com.security.expences.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller()
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ModelAndView unverifiedBandsList(@RequestParam(required = false, name = "error") String error, @RequestParam(required = false, name = "filter") String filter) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("admin/adminPage");
        List<User> userList = userService.listBandsForVerification();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findOneByUserName(userDetails.getUsername());
        modelAndView.addObject("list", userList);
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("isAdmin", currentUser.getUsername().equals("admin"));
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/verify")
    public ModelAndView deleteMedia(@RequestParam("id") int id) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("admin/adminPage");
        userService.verifyBand(id);
        List<User> userList = userService.listBandsForVerification();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findOneByUserName(userDetails.getUsername());
        modelAndView.addObject("list", userList);
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("isAdmin", currentUser.getUsername().equals("admin"));
        return modelAndView;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/disapprove")
    public ModelAndView disapprove(@RequestParam("id") int id) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("admin/adminPage");
        userService.disapprove(id);
        List<User> userList = userService.listBandsForVerification();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findOneByUserName(userDetails.getUsername());
        modelAndView.addObject("list", userList);
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("isAdmin", currentUser.getUsername().equals("admin"));
        return modelAndView;
    }
}

package com.security.expences.controllers;

import com.security.expences.dto.MediaDTO;
import com.security.expences.model.Media;
import com.security.expences.model.Reservation;
import com.security.expences.service.MediaService;
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
@RequestMapping("/media")
public class MediaController {

    @Autowired
    MediaService mediaService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/list")
    public ModelAndView mediaList() throws ParseException {
        ModelAndView modelAndView = new ModelAndView("media/mediaListPage");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Media> mediaList = mediaService.list(userDetails.getUsername());

        modelAndView.addObject("list", mediaList);
        modelAndView.addObject("request", new MediaDTO());
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/createMedia")
    public ModelAndView createMedia(@ModelAttribute @Validated MediaDTO media) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("media/mediaListPage");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Media> mediaList = mediaService.createMedia(media, userDetails.getUsername());
        modelAndView.addObject("list", mediaList);
        modelAndView.addObject("request", new MediaDTO());
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/deleteMedia")
    public ModelAndView deleteMedia(@RequestParam("id") int id) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("media/mediaListPage");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Media> mediaList = mediaService.deleteMedia(id, userDetails.getUsername());
        modelAndView.addObject("list", mediaList);
        modelAndView.addObject("request", new MediaDTO());
        return modelAndView;
    }
}

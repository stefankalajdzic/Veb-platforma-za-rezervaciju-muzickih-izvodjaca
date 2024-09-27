package com.security.expences.controllers;

import com.security.expences.dto.DashboardDTO;
import com.security.expences.dto.MediaDTO;
import com.security.expences.dto.ReservationDTO;
import com.security.expences.model.Media;
import com.security.expences.model.User;
import com.security.expences.service.MediaService;
import com.security.expences.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller()
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/list")
    public ModelAndView mediaList(@RequestParam(required = false, name = "error") String error, @RequestParam(required = false, name = "filter") String filter) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("dashboard/indexPage");

        List<User> userList = userService.listBands(filter);
        List<DashboardDTO> dashboardList = userList.stream().map(user -> {
            DashboardDTO dto = new DashboardDTO();
            dto.setUser(user);
            for (Media m : user.getMediaList()) {
                if (m.getType() != null && m.getType().equals("MAIN")) {
                    dto.setMainPhoto(m.getLink());
                    break;
                }
            }
            return dto;
        }).toList();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findOneByUserName(userDetails.getUsername());
        modelAndView.addObject("list", dashboardList);
        modelAndView.addObject("request", new ReservationDTO());
        modelAndView.addObject("error", error);
        modelAndView.addObject("filter", filter);
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("isBand", !currentUser.getBandName().isEmpty());
        modelAndView.addObject("isAdmin", currentUser.getUsername().equals("admin") || currentUser.getPlatformRole().equalsIgnoreCase("ADMIN"));

        return modelAndView;
    }
}

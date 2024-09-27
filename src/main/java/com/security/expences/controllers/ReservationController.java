package com.security.expences.controllers;

import com.security.expences.dto.ReservationDTO;
import com.security.expences.model.Reservation;
import com.security.expences.service.ReservationService;
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

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    private static final int ACCEPT_STATUS_ID = 2;
    private static final int DECLINE_STATUS_ID = 3;
    @Autowired
    ReservationService reservationService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/reservation")
    public ModelAndView reservation(@ModelAttribute @Validated ReservationDTO reservation) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("reservation/reservationsPage");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Reservation> reservations = reservationService.create(reservation, userDetails.getUsername());
        modelAndView.addObject("list", reservations);
        modelAndView.addObject("username", userDetails.getUsername());
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/reservationChangeStatusAccept")
    public ModelAndView reservationChangeStatusAccept(@RequestParam("id") int id) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("reservation/reservationsPage");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        reservationService.update(id, ACCEPT_STATUS_ID, userDetails.getUsername());
        List<Reservation> reservations = reservationService.receivedReservations(userDetails.getUsername());
        modelAndView.addObject("list", reservations);
        modelAndView.addObject("username", userDetails.getUsername());
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/reservationChangeStatusDecline")
    public ModelAndView reservationChangeStatusDecline(@RequestParam("id") int id) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("reservation/reservationsPage");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        reservationService.update(id, DECLINE_STATUS_ID, userDetails.getUsername());
        List<Reservation> reservations = reservationService.receivedReservations(userDetails.getUsername());
        modelAndView.addObject("list", reservations);
        modelAndView.addObject("username", userDetails.getUsername());
        return modelAndView;
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/receivedReservations")
    public ModelAndView receivedReservations() throws ParseException {
        ModelAndView modelAndView = new ModelAndView("reservation/reservationsPage");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Reservation> reservations = reservationService.receivedReservations(userDetails.getUsername());
        modelAndView.addObject("list", reservations);
        modelAndView.addObject("username", userDetails.getUsername());
        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/madeReservations")
    public ModelAndView madeReservations() throws ParseException {
        ModelAndView modelAndView = new ModelAndView("reservation/reservationsPage");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Reservation> reservations = reservationService.madeReservations(userDetails.getUsername());
        modelAndView.addObject("list", reservations);
        modelAndView.addObject("username", userDetails.getUsername());
        return modelAndView;
    }


}

package com.security.expences.service.impl;

import com.security.expences.dto.ReservationDTO;
import com.security.expences.model.Reservation;
import com.security.expences.model.User;
import com.security.expences.repository.ReservationRepository;
import com.security.expences.repository.StatusRepository;
import com.security.expences.repository.UserRepository;
import com.security.expences.service.ReservationService;
import com.security.expences.service.UserService;
import com.security.expences.util.MusicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final int INIT_STATUS_ID = 1;
    public static final int STATUS_ACCEPTED = 2;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserService userService;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Reservation> create(ReservationDTO reservationDTO, String username) throws ParseException {
        User user = userService.findOneByUserName(username);

        Reservation reservation = new Reservation();
        reservation.setDescription(reservationDTO.getDescription());
        Date reservationTime = new SimpleDateFormat("yyyy-MM-dd").parse(reservationDTO.getTime());
        if(reservationTime.before(new Date())){
            throw new MusicException(HttpStatus.BAD_REQUEST, "Time cannot be in the past!");
        }
        List<Reservation> bookedReservations = reservationRepository.findByTimeAndStatus(reservationTime, statusRepository.getReferenceById(STATUS_ACCEPTED));
        if(!bookedReservations.isEmpty()){
            throw new MusicException(HttpStatus.BAD_REQUEST, "Already booked, please choose another date!");
        }
        reservation.setTime(reservationTime);
        reservation.setUser(user);
        reservation.setNumberOfGuests(reservationDTO.getNumberOfGuests());
        reservation.setLocation(reservationDTO.getLocation());
        reservation.setStatus(statusRepository.getReferenceById(INIT_STATUS_ID));
        reservation.setBand(userRepository.getReferenceById(reservationDTO.getBandId()));

        reservationRepository.save(reservation);
        return reservationRepository.findByUserId(user.getId());
    }

    @Override
    public List<Reservation> madeReservations(String username) throws ParseException {
        User user = userService.findOneByUserName(username);
        return reservationRepository.findByUserId(user.getId());
    }

    @Override
    public List<Reservation> update(int reservationId, int statusId, String username) {
        User user = userService.findOneByUserName(username);
        Reservation reservation = reservationRepository.getReferenceById(reservationId);
        if(reservation.getBand().getId() != user.getId()){
            throw new MusicException(HttpStatus.UNAUTHORIZED);
        }
        reservation.setStatus(statusRepository.getReferenceById(statusId));
        reservationRepository.save(reservation);
        return reservationRepository.findByUserId(user.getId());
    }

    @Override
    public List<Reservation> receivedReservations(String username) throws ParseException {
        User user = userService.findOneByUserName(username);
        return reservationRepository.findByBandId(user.getId());
    }
}

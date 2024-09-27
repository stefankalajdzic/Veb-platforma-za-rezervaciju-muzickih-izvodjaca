package com.security.expences.service;

import com.security.expences.dto.ReservationDTO;
import com.security.expences.model.Reservation;

import java.text.ParseException;
import java.util.List;

public interface ReservationService {

     List<Reservation> create(ReservationDTO reservation, String username) throws ParseException;

     List<Reservation> receivedReservations(String username) throws ParseException;

     List<Reservation> madeReservations(String username) throws ParseException;

     List<Reservation> update(int reservationId, int statusId, String username);
}

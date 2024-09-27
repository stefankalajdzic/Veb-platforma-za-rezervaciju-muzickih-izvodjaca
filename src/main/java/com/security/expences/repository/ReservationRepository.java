package com.security.expences.repository;

import com.security.expences.model.Reservation;
import com.security.expences.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByUserId(int id);
    List<Reservation> findByBandId(int id);
    List<Reservation> findByTimeAndStatus(Date time, Status status);
}

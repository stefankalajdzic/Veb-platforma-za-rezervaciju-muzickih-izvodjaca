package com.security.expences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Status implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "status")
    private List<Reservation> reservationList;
}

package com.security.expences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;



@Getter
@Setter
@Entity
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name = "number_of_guests")
	private int numberOfGuests;

	private String description;

	private String location;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	@ManyToOne
	private Status status;

	@ManyToOne
	private User user;

	@ManyToOne
	private User band;
}
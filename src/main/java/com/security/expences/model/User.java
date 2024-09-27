package com.security.expences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Getter
@Setter
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	private String password;

	private String username;

	private int verified;

	@Column(name = "band_name")
	private String bandName;

	@Column(name = "band_description")
	private String bandDescription;

	@Column(name = "platform_role")
	private String platformRole;

	@Column(name = "band_working_days")
	private String bandWorkingDays;

	@OneToMany(mappedBy = "user")
	private List<Reservation> madeList;

	@OneToMany(mappedBy = "band")
	private List<Reservation> recievedList;

	@OneToMany(mappedBy = "user")
	private List<Media> mediaList;


}
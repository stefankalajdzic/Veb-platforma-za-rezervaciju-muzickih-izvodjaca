package com.security.expences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Media implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String link;

    private String type;

    @ManyToOne
    private User user;
}

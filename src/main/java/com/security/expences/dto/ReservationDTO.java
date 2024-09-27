package com.security.expences.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {
    private int id;
    @NotNull
    @Min(value = 1)
    private int numberOfGuests;
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String description;
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String location;
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String time;
    @NotNull
    @Min(value = 1)
    private int bandId;
}

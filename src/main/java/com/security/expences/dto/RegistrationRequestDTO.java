package com.security.expences.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDTO {
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String username;
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String password;
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String bandName;
    @Size(max = 255)
    private String bandDescription;
    @Size(max = 255)
    private String bandWorkingDays;
    @Size(max = 255)
    private String platformRole;
    @Min(value = 0)
    private int verified;
}

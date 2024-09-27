package com.security.expences.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String username;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String password;
}

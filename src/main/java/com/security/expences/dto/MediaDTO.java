package com.security.expences.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaDTO {
    private int id;
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String link;
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String type;
}

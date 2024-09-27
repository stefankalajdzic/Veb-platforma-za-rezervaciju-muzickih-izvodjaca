package com.security.expences.dto;

import com.security.expences.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashboardDTO {
    private User user;
    private String mainPhoto;
}

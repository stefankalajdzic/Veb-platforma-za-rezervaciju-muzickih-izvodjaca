package com.security.expences.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private HashMap<String, List<String>> validationErrors;
    private String status;
}

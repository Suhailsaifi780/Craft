package com.example.craft.model.dto.response;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ValidationResponse {
    private String status;
    private String message;
}

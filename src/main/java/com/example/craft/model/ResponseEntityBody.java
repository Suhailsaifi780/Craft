package com.example.craft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntityBody {

    private HttpStatus status;
    private int statusCode;
    private String message;
    private Object data;
    private String body;
    private String path;

    public ResponseEntityBody(HttpStatus status, String message, String path, int statusCode, Object data) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.statusCode = statusCode;
        this.data = data;
    }

}


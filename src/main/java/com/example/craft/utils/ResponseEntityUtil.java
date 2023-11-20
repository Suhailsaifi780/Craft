package com.example.craft.utils;

import com.example.craft.model.ResponseEntityBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResponseEntityUtil {

    public static ResponseEntity<?> createResponseEntity (ResponseEntityBody responseEntityBody) {
        log.info("Creating response entity for response body : {}", responseEntityBody);

        if (responseEntityBody.getStatus() != null){
            return new ResponseEntity<>(responseEntityBody, responseEntityBody.getStatus());
        } else {
            return new ResponseEntity<>(responseEntityBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static ResponseEntityBody createResponseEntityBody (String message, HttpStatus httpStatus, String path, String body, Object data) {
        log.info("Inside createResponseEntityBody with body : {} and data : {}", body, data);

        return new ResponseEntityBody(httpStatus, message, path, httpStatus.value(), data);
    }
}

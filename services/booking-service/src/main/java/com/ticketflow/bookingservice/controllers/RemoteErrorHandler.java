package com.ticketflow.bookingservice.controllers;

import feign.FeignException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RemoteErrorHandler {

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Map<String, Object>> eventNotFound(FeignException.NotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("status", 404, "error", "Event not found"));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> remoteFailure(FeignException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("status", 502, "error", "Event service unavailable"));
    }
}

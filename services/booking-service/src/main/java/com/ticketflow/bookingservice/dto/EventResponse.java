package com.ticketflow.bookingservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String city,
        String category,
        LocalDateTime startAt,
        BigDecimal price,
        int capacity,
        int availableSeats,
        boolean published) {
}

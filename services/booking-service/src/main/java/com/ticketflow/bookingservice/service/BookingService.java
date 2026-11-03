package com.ticketflow.bookingservice.service;

import com.ticketflow.bookingservice.client.EventClient;
import com.ticketflow.bookingservice.dto.BookingRequest;
import com.ticketflow.bookingservice.dto.EventResponse;
import com.ticketflow.bookingservice.entities.Booking;
import com.ticketflow.bookingservice.repos.BookingRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final EventClient eventClient;
    private final BookingRepository repository;

    public BookingService(EventClient eventClient, BookingRepository repository) {
        this.eventClient = eventClient;
        this.repository = repository;
    }

    public Booking create(BookingRequest request) {
        EventResponse event = eventClient.getById(request.eventId());
        Booking booking = new Booking(request.customerId(), request.eventId(), request.quantity());
        booking.setTotalPrice(event.price().multiply(BigDecimal.valueOf(request.quantity())));
        return repository.save(booking);
    }
}

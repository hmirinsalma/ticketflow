package com.ticketflow.bookingservice.service;

import com.ticketflow.bookingservice.client.EventClient;
import com.ticketflow.bookingservice.dto.BookingRequest;
import com.ticketflow.bookingservice.dto.EventResponse;
import com.ticketflow.bookingservice.entities.Booking;
import com.ticketflow.bookingservice.repos.BookingRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final EventClient eventClient;
    private final BookingRepository repository;

    public BookingService(EventClient eventClient, BookingRepository repository) {
        this.eventClient = eventClient;
        this.repository = repository;
    }

    @CircuitBreaker(name = "eventClient", fallbackMethod = "createBookingFallback")
    public Booking create(BookingRequest request) {
        EventResponse event = eventClient.getById(request.eventId());

        Booking booking = new Booking(
            request.customerId(),
            request.eventId(),
            request.quantity()
        );

        // Calculer le prix total
        if (event != null && event.price() != null) {
            booking.setTotalPrice(event.price().multiply(
                java.math.BigDecimal.valueOf(request.quantity())
            ));
        }

        return repository.save(booking);
    }

    // Méthode fallback appelée quand le circuit est OPEN
    private Booking createBookingFallback(BookingRequest request, Exception ex) {
        // Option 1: Retourner une erreur explicite
        throw new RuntimeException(
            "Event service is currently unavailable. Please try again later.", ex
        );

        // Option 2 (alternative): Créer une réservation "en attente"
        // Booking booking = new Booking(request.customerId(), request.eventId(), request.quantity());
        // booking.setStatus("PENDING_EVENT_CHECK");
        // return repository.save(booking);
    }
}

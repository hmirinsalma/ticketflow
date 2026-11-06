package com.ticketflow.bookingservice.repos;

import com.ticketflow.bookingservice.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}

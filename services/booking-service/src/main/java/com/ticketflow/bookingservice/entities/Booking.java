package com.ticketflow.bookingservice.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private Long eventId;
    private int quantity;
    private BigDecimal totalPrice;
    private String status;
    private Instant createdAt;

    public Booking() {}

    public Booking(Long customerId, Long eventId, int quantity) {
        this.customerId = customerId;
        this.eventId = eventId;
        this.quantity = quantity;
        this.status = "PENDING_PAYMENT";
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public Long getEventId() { return eventId; }
    public int getQuantity() { return quantity; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}

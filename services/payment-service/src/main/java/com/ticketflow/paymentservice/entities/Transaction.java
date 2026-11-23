package com.ticketflow.paymentservice.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long paymentId;
    
    @Column(nullable = false, length = 50)
    private String type;
    
    @Column(nullable = false)
    private Instant timestamp;
    
    @Column(columnDefinition = "TEXT")
    private String details;
    
    @PrePersist
    protected void onCreate() {
        timestamp = Instant.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}

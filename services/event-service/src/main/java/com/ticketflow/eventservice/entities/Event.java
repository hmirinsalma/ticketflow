package com.ticketflow.eventservice.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String city;
    private String category;
    private LocalDateTime startAt;
    private BigDecimal price;
    private int capacity;
    private int availableSeats;
    private boolean published;

    public Event() {}

    public Event(String title, String city, String category, LocalDateTime startAt,
                 BigDecimal price, int capacity, boolean published) {
        this.title = title;
        this.city = city;
        this.category = category;
        this.startAt = startAt;
        this.price = price;
        this.capacity = capacity;
        this.availableSeats = capacity;
        this.published = published;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getCity() { return city; }
    public String getCategory() { return category; }
    public LocalDateTime getStartAt() { return startAt; }
    public BigDecimal getPrice() { return price; }
    public int getCapacity() { return capacity; }
    public int getAvailableSeats() { return availableSeats; }
    public boolean isPublished() { return published; }
}
package com.ticketflow.eventservice;

import com.ticketflow.eventservice.entities.Event;
import com.ticketflow.eventservice.repos.EventRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final EventRepository repository;

    public DataLoader(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        repository.save(new Event("Concert Jazz", "Casablanca", "MUSIC",
                LocalDateTime.now().plusDays(30), new BigDecimal("150.00"), 100, true));
        repository.save(new Event("Match de gala", "Rabat", "SPORT",
                LocalDateTime.now().plusDays(45), new BigDecimal("80.00"), 500, true));
        repository.save(new Event("Brouillon prive", "Marrakech", "THEATRE",
                LocalDateTime.now().plusDays(60), new BigDecimal("50.00"), 50, false));
    }
}
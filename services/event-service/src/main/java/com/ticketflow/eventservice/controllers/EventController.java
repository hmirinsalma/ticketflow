package com.ticketflow.eventservice.controllers;

import com.ticketflow.eventservice.entities.Event;
import com.ticketflow.eventservice.repos.EventRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventRepository repository;

    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Event> list() {
        return repository.findByPublishedTrue();
    }

    @GetMapping("/{id}")
    public Event get(@PathVariable Long id) {
        return repository.findByIdAndPublishedTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }
}
package com.ticketflow.eventservice.repos;

import com.ticketflow.eventservice.entities.Event;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByPublishedTrue();
    Optional<Event> findByIdAndPublishedTrue(Long id);
}
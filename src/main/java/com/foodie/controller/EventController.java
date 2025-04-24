package com.foodie.controller;

import com.foodie.model.Event;
import com.foodie.service.EventService;
import com.foodie.request.CreateEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Create an Event
    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Event> createEvent(@PathVariable Long restaurantId,
                                             @RequestBody @Validated CreateEventRequest request) {
        Event createdEvent = eventService.createEvent(restaurantId, request);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    // Get all events
    @GetMapping("/")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Get events for a specific restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Event>> getRestaurantEvents(@PathVariable Long restaurantId) {
        List<Event> events = eventService.getEventsByRestaurant(restaurantId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Delete an event
    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }
}

package com.foodie.service;

import com.foodie.model.Event;
import com.foodie.request.CreateEventRequest;

import java.util.List;

public interface EventService {
    Event createEvent(Long restaurantId, CreateEventRequest request);
    List<Event> getAllEvents();
    List<Event> getEventsByRestaurant(Long restaurantId);
    void deleteEvent(Long eventId);
}

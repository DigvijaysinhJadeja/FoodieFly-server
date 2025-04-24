package com.foodie.service;

import com.foodie.model.Event;
import com.foodie.model.Restaurant;
import com.foodie.repository.EventRepository;
import com.foodie.repository.RestaurantRepository;
import com.foodie.request.CreateEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImp implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Event createEvent(Long restaurantId, CreateEventRequest request) {
        Event event = new Event();
        event.setName(request.getName());
        event.setLocation(request.getLocation());
        event.setImage(request.getImage());
        event.setStartedAt(request.getStartedAt());
        event.setEndsAt(request.getEndsAt());

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                new IllegalArgumentException("Restaurant not found"));

        event.setRestaurant(restaurant);

        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getEventsByRestaurant(Long restaurantId) {
        return eventRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}

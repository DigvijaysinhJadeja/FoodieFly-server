package com.foodie.service;

import com.foodie.dto.RestaurantDto;
import com.foodie.model.Address;
import com.foodie.model.Restaurant;
import com.foodie.model.User;
import com.foodie.repository.AddressRepository;
import com.foodie.repository.RestaurantRepository;
import com.foodie.repository.UserRepository;
import com.foodie.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address = addressRepository.save(req.getAddress()); // our address will be saved here

        Restaurant restaurant = new Restaurant(); // created a new restaurant
        restaurant.setAddress(address); // setting the address of the new restaurant
        restaurant.setContactInformation(req.getContactInformation()); // setting the contact info
        restaurant.setCuisineType(req.getCuisineType()); // setting the cusine type
        restaurant.setDescription(req.getDescription()); // setting the description of the restaurant
        restaurant.setImages(req.getImages()); // setting the images of the restaurant
        restaurant.setName(req.getName()); // setting the restaurant name.
        restaurant.setOpeningHours(req.getOpeningHours()); // setting the opening hour of restaurant.
        restaurant.setRegistrationDate(LocalDateTime.now());// for registering a new restaurant its time is to be set
        restaurant.setOwner(user);// setting up the owner of new restaurant.


        return restaurantRepository.save(restaurant); // saving all the data of new restaurant
    }

    @Override
    public Restaurant updateRestaurant(Long RestaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        Restaurant restaurant = findRestaurantById(RestaurantId);

        //updating cuisine type of restaurant
        if(restaurant.getCuisineType()!=null) {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }

        //updating description of restaurant
        if(restaurant.getDescription()!=null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }

        //updating image of the restaurant
        if(restaurant.getImages()!=null) {
            restaurant.setImages(updatedRestaurant.getImages());
        }

        //updating name of restaurant
         if(restaurant.getName()!=null) {
             restaurant.setName(updatedRestaurant.getName());
         }
        // updating opening hours of restaurant
         if(restaurant.getOpeningHours()!=null) {
             restaurant.setOpeningHours(updatedRestaurant.getOpeningHours());
         }
         // updating contact information of the restaurant
         if(restaurant.getContactInformation()!=null){
             restaurant.setContactInformation(updatedRestaurant.getContactInformation());
         }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long RestaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(RestaurantId); // find restaurant by id will throw exception for us automatically
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if(opt.isEmpty()) { // here this will give us the exception
            throw new Exception("Restaurant not found with id : " + id);
        }
        return opt.get();
    }

    @Override
    public Restaurant findRestaurantByUserId(Long UserId) {
        Restaurant restaurant = restaurantRepository.findByOwnerId(UserId);
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long RestaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(RestaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurant.getId());

        boolean isFavorited = false;
        List<RestaurantDto>favorites = user.getFavorites();
        for(RestaurantDto favorite : favorites) {
            if(favorite.getId().equals(RestaurantId)){
                isFavorited = true;
                break;
            }
        }

        if(isFavorited) {
            favorites.removeIf(favorite->favorite.getId().equals(RestaurantId));
        }else{
            favorites.add(dto);
        }
        userRepository.save(user);

        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen()); // this which change the status if it is open it will close and if it is close it will open
        return restaurantRepository.save(restaurant);
    }
}

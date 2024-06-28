package com.masai.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.masai.exception.NotFoundException;
import com.masai.model.Restaurant;



@Service
public class RestaurantService {
    private List<Restaurant> restaurants = new ArrayList<>();

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurants;
    }

    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurants.stream()
                .filter(r -> r.getRestaurantId().equals(restaurantId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Restaurant not found with id: " + restaurantId));
    }
}


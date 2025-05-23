package com.foodie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodie.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullname;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore // whenever i fetch user i do not need the order list nad some times may create null values too
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")  // user have one to many relation with order entity
    private List<Order> orders = new ArrayList< >();

    @ManyToMany
    @JoinTable(
            name = "user_favorite_restaurants",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> favorites = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true) // whenever we delete user we need to delete all the addresses related to him.
    private List<Address>addresses =  new ArrayList<>();

}

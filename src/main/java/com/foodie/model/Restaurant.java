package com.foodie.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User owner;

    private String name;

    private String description;

    private String cuisineType;

    @OneToOne
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String  openingHours;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @ElementCollection // used for creating a seperate table
    @Column(length = 1000)
    private List<String>images;

    private LocalDateTime registrationDate;

    private boolean open;

    @JsonIgnore // whenever I fetch restaurant object i dont need food list
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL, orphanRemoval = true) //whenever we remove restaurant food automatically get removed from list
    private List<Food> foods = new ArrayList<>();





}

package com.foodie.repository;

import com.foodie.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> { // first shows for which entity we create repository,second shows the unique identifiers type of entity.
    public User findByEmail(String username); // this will find user by email
}

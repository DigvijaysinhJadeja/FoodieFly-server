package com.foodie.service;

import com.foodie.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception; // finding user

    public User findUserByEmail(String email) throws Exception;

}

package com.foodie.response;

import com.foodie.model.USER_ROLE;
import com.foodie.model.User;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;

    private String message;

    private USER_ROLE role;

    private String fullname;
}

package com.foodie.service;

import com.foodie.model.Order;
import com.foodie.response.PaymentResponse;
import com.stripe.exception.StripeException;
import lombok.Data;


public interface PaymentService  {

    public PaymentResponse createPaymentLink(Order order) throws StripeException;

}

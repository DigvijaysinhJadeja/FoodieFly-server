package com.foodie.service;

import com.foodie.model.Order;
import com.foodie.response.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImp implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripSecretKey; // i need to store the value of strip SKey from the aplication.proprties

    @Override
    public PaymentResponse createPaymentLink(Order order) throws StripeException {
        Stripe.apiKey = stripSecretKey;

         SessionCreateParams params = SessionCreateParams.builder().addPaymentMethodType(
                 SessionCreateParams.
                 PaymentMethodType.CARD).
                 setMode(SessionCreateParams.Mode.PAYMENT).
                 setSuccessUrl("https://foodie-fly.vercel.app/payment/success/"+order.getId()).
                 setCancelUrl("https://foodie-fly.vercel.app/payment/fail").
                 addLineItem(SessionCreateParams.LineItem.builder().
                         setQuantity(1L).setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                 .setCurrency("usd")
                                 .setUnitAmount((long)order.getTotalPrice()*100)
                                 .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                         .setName("FoodieFly")
                                         .build()
                                 ).build()
                         ).build()
                 ).build();

        Session session = Session.create(params);

        PaymentResponse response = new PaymentResponse();
        response.setPayment_url(session.getUrl());

        return response;
    }
}

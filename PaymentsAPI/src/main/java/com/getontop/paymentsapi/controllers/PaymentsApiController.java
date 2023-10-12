package com.getontop.paymentsapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.getontop.clients.payments.models.requestObject.ExternalPayment;
import com.getontop.clients.payments.models.responseObject.PaymentResponse;
import com.getontop.paymentsapi.service.PaymentsApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentsApiController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentsApiService paymentsApiService;

    @PostMapping()
    public PaymentResponse getPayments(@RequestBody ExternalPayment externalPayment) {
        return paymentsApiService.getPaymentsStatus(externalPayment);
    }

    @PostMapping("/transfer")
    public ResponseEntity<PaymentResponse> transfer(@RequestBody ExternalPayment externalPayment)  {
        // log this transaction

        return paymentsApiService.transfer(externalPayment);
    }




}

package com.getontop.clients.payments.models.responseObject;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    public RequestInfo requestInfo;
    public PaymentInfo paymentInfo;
}
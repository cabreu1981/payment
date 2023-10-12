package com.getontop.clients.payments.models.requestObject;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalPayment {
    public Source source;
    public Destination destination;
    public double amount;
}
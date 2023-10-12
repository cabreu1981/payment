package com.getontop.clients.payments;

import com.getontop.clients.payments.models.requestObject.ExternalPayment;
import com.getontop.clients.payments.models.responseObject.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

;

@FeignClient(
        value = "payments-service",
        path = "api/v1/payments"
)
public interface PaymentClient {

    @PostMapping()
    PaymentResponse getPaymentsStatus(ExternalPayment externalPayment);

}

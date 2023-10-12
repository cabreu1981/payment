package com.getontop.clients.TransactionsHistory.models.requestObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private String userId;
    private Double amount;
    private String fee;
    private String accountNumber;
    private String transactionType;
    private String transactionStatus;
}

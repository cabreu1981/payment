package com.getontop.clients.TransactionsHistory.models;


import com.getontop.clients.TransactionsHistory.models.enums.TransactionStatus;
import com.getontop.clients.TransactionsHistory.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {

    private Integer transactionId;
    private Integer userId;
    private Double amount;
    private String accountNumber;
    private double fee;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private LocalDateTime transactionDate;

}




package com.getontop.clients.wallet.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet_transactions",
        schema = "public",
        catalog = "payments"
)

public class WalletTransactions {

    @Id
    private Integer transactionId;
    private Integer userId;
    private Double amount;
    private String transactionType;
    private DateTime transactionDate;
    private String transactionStatus;
}


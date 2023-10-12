package com.getontop.clients.user.models;

import com.getontop.clients.TransactionsHistory.models.Transactions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String name;
    private String surName;
    private String nationalId;
    private String bankName;
    private String accountNumber;
    private String routingNumber;

}

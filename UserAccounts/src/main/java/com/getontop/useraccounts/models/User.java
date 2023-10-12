package com.getontop.useraccounts.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
        schema = "public",
        catalog = "payments"
    )
public class User {

    @Id
    @SequenceGenerator(name = "users_id_sequence", sequenceName = "users_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_sequence")
    private Integer userId;
    private String name;
    private String surName;
    private String nationalId;
    private String bankName;
    private String accountNumber;
    private String routingNumber;

    public User(User user) {
            this.userId = user.getUserId();
            this.name = user.getName();
            this.surName = user.getSurName();
            this.nationalId = user.getNationalId();
            this.bankName = user.getBankName();
            this.accountNumber = user.getAccountNumber();
            this.routingNumber = user.getRoutingNumber();
    }
}

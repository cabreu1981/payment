package com.getontop.transactions.models;



import com.getontop.transactions.models.enums.TransactionStatus;
import com.getontop.transactions.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @SequenceGenerator(name = "transactions_id_sequence", sequenceName = "transactions_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactions_id_sequence")
    private Integer transactionId;
    private Integer userId;
    private Double amount;
    private String accountNumber;
    private double fee;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private LocalDateTime transactionDate;





}




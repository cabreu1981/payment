package com.getontop.transactions.repository;


import com.getontop.transactions.models.Transactions;
import com.getontop.transactions.models.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

    List<Transactions> findByUserId(Integer userId);

    List<Transactions> findByTransactionStatus(TransactionStatus status);

    @Query(value = "SELECT * FROM transactions WHERE account_number = ?1", nativeQuery = true)
    List<Transactions> getTransactionsByAccount(@Param("accountNumber") String accountNumber);

    @Query(value = "SELECT * FROM transactions WHERE account_number = ?1 AND transaction_date = ?2", nativeQuery = true)
    List<Transactions> getTransactionsByAccountAndDate(@Param("accountNumber") String accountNumber, @Param("date") String date);


}
package com.getontop.transactions.service;

import com.getontop.transactions.models.Transactions;
import com.getontop.transactions.models.enums.TransactionStatus;
import com.getontop.transactions.models.enums.TransactionType;
import com.getontop.transactions.models.requestObject.TransactionRequest;
import com.getontop.transactions.repository.TransactionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    public void saveTransaction(TransactionRequest transationsRequest) {
        if (transationsRequest.getAmount() < 0) {
            transationsRequest.setTransactionStatus(TransactionStatus.FAILED.getValue());
        }
        Transactions transaction = new Transactions();
        transaction.setAmount(transationsRequest.getAmount());
        transaction.setUserId(Integer.valueOf(transationsRequest.getUserId()));
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.valueOf(transationsRequest.getTransactionType()));
        transaction.setFee(Double.parseDouble(transationsRequest.getFee()));
        transaction.setTransactionStatus(TransactionStatus.valueOf(transationsRequest.getTransactionStatus()));
        transaction.setAccountNumber(transationsRequest.getAccountNumber());
        transactionsRepository.saveAndFlush(transaction);

    }

    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    public List<Transactions> getTransactionsByUserId(Integer userId) {
        return transactionsRepository.findByUserId(userId);
    }

    public List<Transactions> getTransactionsByAccountNumber(String accountNumber) {
        return transactionsRepository.getTransactionsByAccount(accountNumber);
    }


}

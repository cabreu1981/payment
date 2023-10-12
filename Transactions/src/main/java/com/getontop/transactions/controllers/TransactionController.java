package com.getontop.transactions.controllers;




import com.getontop.transactions.models.Transactions;
import com.getontop.transactions.models.requestObject.TransactionRequest;
import com.getontop.transactions.service.TransactionsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
@AllArgsConstructor
public class TransactionController {


    private TransactionsService transactionService;

    @PostMapping
    public void createTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionService.saveTransaction(transactionRequest);
    }

    @GetMapping("get/{accountNumber}")
    public List<Transactions> getTransactionsByAccount(@PathVariable("accountNumber") String accountNumber) {
        List<Transactions> transactionsByAccountNumber = transactionService.getTransactionsByAccountNumber(accountNumber);
        transactionsByAccountNumber.sort(new TransactionComparator());
        return transactionsByAccountNumber;
    }

    static class TransactionComparator implements java.util.Comparator<Transactions> {
        @Override
        public int compare(Transactions a, Transactions b) {
            return a.getTransactionDate().compareTo(b.getTransactionDate());
        }
    }

}

package com.getontop.clients.TransactionsHistory;

import com.getontop.clients.TransactionsHistory.models.Transactions;
import com.getontop.clients.TransactionsHistory.models.requestObject.TransactionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        value = "Transactions",
        path = "api/v1/transactions"
)
public interface TransactionsClient {

    @PostMapping
    void createTransaction(@RequestBody TransactionRequest transactionRequest);

    @GetMapping("get/{accountNumber}")
     List<Transactions> getTransactionsByAccount(@PathVariable("accountNumber") String accountNumber);
}

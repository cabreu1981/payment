package com.getontop.useraccounts.models;

import com.getontop.clients.TransactionsHistory.models.Transactions;

import java.util.List;

public class UserWithTransactions extends User {
    private final List<Transactions> transactions;

    public UserWithTransactions(User user, List<Transactions> transactions) {
        super(user);
        // Copy other user properties if needed
        this.transactions = transactions;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }
}

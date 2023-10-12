package com.getontop.useraccounts.service;

import com.getontop.clients.TransactionsHistory.TransactionsClient;
import com.getontop.clients.TransactionsHistory.models.Transactions;
import com.getontop.useraccounts.exceptions.UserNotFoundException;
import com.getontop.useraccounts.models.User;
import com.getontop.useraccounts.models.UserWithTransactions;
import com.getontop.useraccounts.models.requestObjects.UserRegistrationRequest;
import com.getontop.useraccounts.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionsClient transactionsClient;

    public void registerUser(UserRegistrationRequest request) {
        User user = User.builder()
                .name(request.name())
                .surName(request.surName())
                .nationalId(request.nationalId())
                .bankName(request.bankName())
                .accountNumber(request.accountNumber())
                .routingNumber(request.routingNumber())
                .build();

        //todo: check if nationalID is unique
        //todo: check if nationalID is valid
        //todo: check if nationalID is taken
        userRepository.saveAndFlush(user); //store created user int database

    }

    public User getUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public ResponseEntity<User> getUserByNationalId(String nationalId) {
        Optional<User> user = userRepository.findUserByNationalId(nationalId);
        return ResponseEntity.ok(user.orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    public ResponseEntity getUserByAccountNumber(String accountNumber) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByaccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("User not found")));
        List<Transactions> transactions = userOptional
                .map(user -> transactionsClient.getTransactionsByAccount(user.getAccountNumber()))
                .orElseThrow(() -> new RuntimeException("Transactions not found"));

        UserWithTransactions userWithTransactions = userOptional
                .map(user -> new UserWithTransactions(user, transactions))
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Transactions> userTransactions = userWithTransactions.getTransactions();
        return ResponseEntity.ok(userWithTransactions);
    }


}

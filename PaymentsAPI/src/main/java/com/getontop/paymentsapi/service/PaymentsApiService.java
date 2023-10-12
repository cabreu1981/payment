package com.getontop.paymentsapi.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getontop.clients.TransactionsHistory.TransactionsClient;
import com.getontop.clients.TransactionsHistory.models.requestObject.TransactionRequest;
import com.getontop.clients.payments.models.requestObject.ExternalPayment;
import com.getontop.clients.payments.models.responseObject.PaymentResponse;
import com.getontop.clients.user.UserClient;
import com.getontop.clients.user.models.User;
import com.getontop.clients.wallet.WalletClient;
import com.getontop.clients.wallet.models.requestObject.WalleRequestObject;
import com.getontop.clients.wallet.models.requestObject.WalletRequestBalance;
import com.getontop.clients.wallet.models.responseObject.WalletResponseObject;
import com.getontop.paymentsapi.Utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PaymentsApiService {

    private RestTemplate restTemplate;

    private  WalletClient walletClient;

    private UserClient userClient;

    private TransactionsClient transactionClient;

    public PaymentResponse getPaymentsStatus(ExternalPayment externalPayment) {
        PaymentResponse paymentResponse = restTemplate.postForObject("http://mockoon.tools.getontop.com:3000/api/v1/payments",
                externalPayment,
                PaymentResponse.class,
                externalPayment);
        return new PaymentResponse(Objects.requireNonNull(paymentResponse).requestInfo, Objects.requireNonNull(paymentResponse).paymentInfo);
    }

    public ResponseEntity<PaymentResponse> transfer(ExternalPayment externalPayment) {
        // Apply a 10% fee to the transfer amount
        double amountWithNoFee = externalPayment.getAmount();


        //create the transaction for the history to be saved
        TransactionRequest transactionRequest = new TransactionRequest();
        try {
            PaymentResponse paymentResponse = sendPaymentRequest(externalPayment);

            //get the user
            ResponseEntity<User> userByAccountNumber =
                    userClient.getUserByAccountNumber(externalPayment.getSource().account.accountNumber);

            //create the wallet request object
            WalleRequestObject walleRequestObject =
                    new WalleRequestObject(Objects.requireNonNull(userByAccountNumber.getBody()).getUserId(),
                            (int) externalPayment.getAmount());

            //check if the user has enough balance to make the transaction
            WalletRequestBalance walletBalance = walletClient
                    .getWalletBalance(userByAccountNumber.getBody().getUserId().toString());

            ResponseEntity<PaymentResponse> BAD_REQUEST =
                    getPaymentResponseResponseEntity(externalPayment, amountWithNoFee, transactionRequest,
                            paymentResponse, userByAccountNumber, walletBalance);
            if (BAD_REQUEST != null) return BAD_REQUEST;

            // Apply a 10% fee to the transfer amount
            externalPayment.setAmount(applyFee(externalPayment.getAmount()));

            //deduct the amount from the user's wallet
            WalletResponseObject walletResponseObject = walletClient.withdrawWallet(walleRequestObject);

            //add the transaction to the internal database
            extracted(externalPayment, amountWithNoFee, transactionRequest, userByAccountNumber);

            return ResponseEntity.ok(paymentResponse);
        } catch (HttpServerErrorException ex) {
            PaymentResponse paymentResponse = handleInternalServerError(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(paymentResponse);
        }
    }

    private void extracted(ExternalPayment externalPayment,
                           double amountWithNoFee,
                           TransactionRequest transactionRequest, ResponseEntity<User> userByAccountNumber) {
        transactionRequest.setTransactionType("Withdraw");
        transactionRequest.setUserId(Objects.requireNonNull(userByAccountNumber.getBody()).getUserId().toString());
        transactionRequest.setAmount(externalPayment.getAmount());
        transactionRequest.setAccountNumber(externalPayment.getSource().account.accountNumber);
        transactionRequest.setTransactionStatus("IN_PROGRESS");
        transactionRequest.setFee(String.valueOf(amountWithNoFee * Constants.GET_FEE_FROM_TOTAL)); // 10% fee
        transactionClient.createTransaction(transactionRequest);
    }

    private ResponseEntity<PaymentResponse> getPaymentResponseResponseEntity(
            ExternalPayment externalPayment, double amountWithNoFee,
            TransactionRequest transactionRequest, PaymentResponse paymentResponse,
            ResponseEntity<User> userByAccountNumber, WalletRequestBalance walletBalance)
    {
        if(walletBalance.getBalance() < externalPayment.getAmount()){
            transactionRequest.setTransactionStatus("FAILED");
            transactionRequest.setFee(String.valueOf(0.0));
            transactionRequest.setTransactionType("Withdraw");
            transactionRequest.setUserId(Objects.requireNonNull(userByAccountNumber.getBody()).getUserId().toString());
            transactionRequest.setAmount(amountWithNoFee);
            transactionRequest.setAccountNumber(externalPayment.getSource().account.accountNumber);
            transactionClient.createTransaction(transactionRequest);

            paymentResponse.requestInfo.status = "FAILED";
            paymentResponse.requestInfo.error = "Insufficient funds";

            paymentResponse.paymentInfo.id = userByAccountNumber.getBody().getUserId().toString();
            paymentResponse.paymentInfo.amount = externalPayment.getAmount();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(paymentResponse);
        }
        return null;
    }


    // Send the payment request to the external API
    private PaymentResponse sendPaymentRequest(ExternalPayment externalPayment) {
        return restTemplate.postForObject(
                "http://mockoon.tools.getontop.com:3000/api/v1/payments",
                externalPayment,
                PaymentResponse.class,
                externalPayment);
    }

    // Apply a 10% fee to the transfer amount
    private double applyFee(double amount) {
        return amount * Constants.FEE_PERCENTAGE;
    }

    // Handle internal server error and return a PaymentResponse
    private PaymentResponse handleInternalServerError(HttpServerErrorException ex) {
        try {
            return getPaymentResponseFromJson(ex);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // Parse the PaymentResponse from the error response JSON
    private PaymentResponse getPaymentResponseFromJson(HttpServerErrorException ex) throws JsonProcessingException {
        HttpStatus statusCode = ex.getStatusCode();
        String responseBody = ex.getResponseBodyAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, PaymentResponse.class);
    }

}

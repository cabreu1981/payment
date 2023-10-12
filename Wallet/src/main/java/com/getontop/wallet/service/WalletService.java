package com.getontop.wallet.service;

import com.getontop.clients.wallet.models.requestObject.WalleRequestObject;
import com.getontop.clients.wallet.models.requestObject.WalletRequestBalance;
import com.getontop.clients.wallet.models.responseObject.WalletResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Service
public class WalletService {


    @Autowired
    private RestTemplate restTemplate;


    //get wallet balance
    public WalletRequestBalance  getWalletBalance(String userId) {
        WalletRequestBalance walletRequestBalance = restTemplate.getForObject("http://mockoon.tools.getontop.com:3000/wallets/balance?user_id=" + userId, WalletRequestBalance.class,  userId);
        return new WalletRequestBalance(Objects.requireNonNull(walletRequestBalance).balance, walletRequestBalance.user_id);
    }

    //top up wallet
    public WalletResponseObject topUpWallet(WalleRequestObject walletRequest) {
        return restTemplate.postForObject("http://mockoon.tools.getontop.com:3000/wallets/transactions",
                walletRequest,
                WalletResponseObject.class,
                walletRequest);
    }

    //withdraw wallet
    public WalletResponseObject withdrawWallet(WalleRequestObject walletRequest) {
        return restTemplate.postForObject("http://mockoon.tools.getontop.com:3000/wallets/transactions",
                walletRequest,
                WalletResponseObject.class,
                walletRequest);
    }

}

package com.getontop.wallet.controllers;

import com.getontop.clients.wallet.models.requestObject.WalleRequestObject;
import com.getontop.clients.wallet.models.requestObject.WalletRequestBalance;
import com.getontop.clients.wallet.models.responseObject.WalletResponseObject;
import com.getontop.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/v1/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    //top up wallet
    @PostMapping(value = "/topup")
    public WalletResponseObject topUpWallet(@RequestBody WalleRequestObject walletRequest) {
         return walletService.topUpWallet(walletRequest);
    }

    //withdraw from wallet
    @PostMapping("/withdraw")
    public WalletResponseObject withdrawWallet(@RequestBody WalleRequestObject walletRequest) {
         return walletService.withdrawWallet(walletRequest);
    }


    //get wallet balance
    @GetMapping("/balance/{userId}")
    public WalletRequestBalance getWalletBalance(@PathVariable("userId")  String userId) {
        return walletService.getWalletBalance(userId);
    }


}

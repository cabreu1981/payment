package com.getontop.clients.wallet;

import com.getontop.clients.wallet.models.requestObject.WalleRequestObject;
import com.getontop.clients.wallet.models.requestObject.WalletRequestBalance;
import com.getontop.clients.wallet.models.responseObject.WalletResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "wallet-service",
        path = "api/v1/wallets"
)
public interface WalletClient {
    @GetMapping("/balance/{userId}")
    WalletRequestBalance getWalletBalance(@PathVariable("userId")  String userId);

    @PostMapping(value = "/topup")
    WalletResponseObject topUpWallet(@RequestBody WalleRequestObject walletRequest);

    @PostMapping("/withdraw")
    WalletResponseObject withdrawWallet(@RequestBody WalleRequestObject walletRequest);


}

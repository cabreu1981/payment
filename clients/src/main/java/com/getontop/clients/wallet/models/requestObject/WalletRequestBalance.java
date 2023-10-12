package com.getontop.clients.wallet.models.requestObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletRequestBalance {
    public Integer balance;
    public Integer user_id;
}

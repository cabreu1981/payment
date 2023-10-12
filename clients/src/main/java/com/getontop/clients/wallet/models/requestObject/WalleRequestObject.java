package com.getontop.clients.wallet.models.requestObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalleRequestObject {
    public Integer user_id;
    public Integer amount;
}

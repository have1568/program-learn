package net.wang.axon.account.event;

import lombok.Data;

@Data
public class AccountWithDrawedEvent {
    private String accountId;

    private Double amount;

    public AccountWithDrawedEvent(String accountId, Double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }
}

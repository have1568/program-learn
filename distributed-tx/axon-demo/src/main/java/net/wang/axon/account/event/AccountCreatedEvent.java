package net.wang.axon.account.event;

import lombok.Data;

@Data
public class AccountCreatedEvent {
    private String accountId;

    public AccountCreatedEvent(String accountId) {
        this.accountId = accountId;
    }
}

package net.wang.axon.account.query;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 物化视图对象
 */
@Data
@NoArgsConstructor
@Entity(name = "a_account")
public class AccountEntity {

    @Id
    private String accountId;

    private Double deposit;

    public AccountEntity(String accountId) {
        this.accountId = accountId;
        this.deposit = 0D;
    }
}

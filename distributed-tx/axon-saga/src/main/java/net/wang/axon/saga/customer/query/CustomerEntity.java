package net.wang.axon.saga.customer.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 物化视图（保存在数据库的对象）
 */
@Entity(name = "c_customer")
@Data
@NoArgsConstructor
public class CustomerEntity {

    @Id
    private String customerId;

    private String username;

    private String password;

    private Double deposit;

    public CustomerEntity(String customerId, String username, String password, Double deposit) {
        this.customerId = customerId;
        this.username = username;
        this.password = password;
        this.deposit = deposit;
    }
}

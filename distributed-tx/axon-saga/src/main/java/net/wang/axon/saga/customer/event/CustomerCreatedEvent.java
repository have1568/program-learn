package net.wang.axon.saga.customer.event;

import lombok.Data;

/**
 * 用户创建事件  创建用户需要4个属性
 */
@Data
public class CustomerCreatedEvent {

    private String customerId;

    private String username;

    private String password;

    private Double deposit;

    public CustomerCreatedEvent(String customerId, String username, String password, Double deposit) {
        this.customerId = customerId;
        this.username = username;
        this.password = password;
        this.deposit = deposit;
    }
}

package net.wang.axon.saga.customer.event;

import lombok.Data;

/**
 * 用户消费事件 需要用户ID和消费金额
 */
@Data
public class CustomerChargedEvent {

    private String customerId;

    private Double amount; //消费金额

    public CustomerChargedEvent(String customerId, Double amount) {
        this.customerId = customerId;
        this.amount = amount;
    }
}

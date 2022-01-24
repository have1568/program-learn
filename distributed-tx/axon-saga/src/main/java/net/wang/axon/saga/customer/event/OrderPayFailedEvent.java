package net.wang.axon.saga.customer.event;

import lombok.Data;

/**
 * 用户消费事件 需要用户ID和消费金额
 */
@Data
public class OrderPayFailedEvent {

    private String orderId;

    private String customerId;

    private Double amount;


    public OrderPayFailedEvent(String orderId, String customerId, Double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
    }
}

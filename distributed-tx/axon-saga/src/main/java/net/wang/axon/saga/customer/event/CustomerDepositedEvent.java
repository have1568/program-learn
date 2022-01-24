package net.wang.axon.saga.customer.event;


import lombok.Data;

/**
 * 用户存款事件，需要用户ID和存款金额
 */
@Data
public class CustomerDepositedEvent {

    private String customerId;

    private Double amount; //存款金额

    public CustomerDepositedEvent(String customerId, Double amount) {
        this.customerId = customerId;
        this.amount = amount;
    }
}

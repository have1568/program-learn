package net.wang.axon.saga.customer.command;


import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class OrderPayCommand {

    @TargetAggregateIdentifier
    private String customerId;

    private String orderId;

    private Double amount;

    public OrderPayCommand(String orderId, String customerId, Double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
    }
}

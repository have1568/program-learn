package net.wang.axon.saga.order.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
public class OrderCreateCommand {

    @TargetAggregateIdentifier
    private String orderId;

    private String customerId;

    private String ticketId;

    private Double amount;

    private String title;

    public OrderCreateCommand(String orderId, String customerId, String ticketId, Double amount, String title) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.title = title;
    }
}

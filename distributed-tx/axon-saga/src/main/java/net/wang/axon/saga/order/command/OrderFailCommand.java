package net.wang.axon.saga.order.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
public class OrderFailCommand {

    @TargetAggregateIdentifier
    private String orderId;

    private String reason;

    public OrderFailCommand(String orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }
}

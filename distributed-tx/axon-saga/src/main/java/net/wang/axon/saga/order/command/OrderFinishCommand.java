package net.wang.axon.saga.order.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
public class OrderFinishCommand {

    @TargetAggregateIdentifier
    private String orderId;


    public OrderFinishCommand(String orderId) {
        this.orderId = orderId;
    }
}

package net.wang.axon.saga.ticket.command;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class OrderTicketMoveCommand {

    @TargetAggregateIdentifier
    private String ticketId;

    private String orderId;

    private String customerId;

    public OrderTicketMoveCommand(String ticketId, String orderId, String customerId) {
        this.ticketId = ticketId;
        this.orderId = orderId;
        this.customerId = customerId;
    }
}

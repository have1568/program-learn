package net.wang.axon.saga.ticket.command;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class OrderTicketPreserveCommand {

    @TargetAggregateIdentifier
    private String ticketId;

    private String orderId;

    private String customerId;

    public OrderTicketPreserveCommand(String ticketId, String orderId, String customerId) {
        this.ticketId = ticketId;
        this.orderId = orderId;
        this.customerId = customerId;
    }
}

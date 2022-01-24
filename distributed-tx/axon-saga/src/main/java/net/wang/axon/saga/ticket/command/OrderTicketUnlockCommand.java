package net.wang.axon.saga.ticket.command;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class OrderTicketUnlockCommand {

    @TargetAggregateIdentifier
    private String ticketId;

    private String customerId;

    public OrderTicketUnlockCommand(String ticketId, String customerId) {
        this.ticketId = ticketId;
        this.customerId = customerId;
    }
}

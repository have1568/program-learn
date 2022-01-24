package net.wang.axon.saga.ticket.command;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class TicketCreateCommand {

    @TargetAggregateIdentifier
    private String ticketId;

    private String name;

    public TicketCreateCommand(String ticketId, String name) {
        this.ticketId = ticketId;
        this.name = name;
    }
}

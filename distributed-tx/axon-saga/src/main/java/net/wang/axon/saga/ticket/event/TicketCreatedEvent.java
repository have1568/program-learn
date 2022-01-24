package net.wang.axon.saga.ticket.event;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class TicketCreatedEvent {


    private String ticketId;

    private String name;

    public TicketCreatedEvent(String ticketId, String name) {
        this.ticketId = ticketId;
        this.name = name;
    }
}

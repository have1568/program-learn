package net.wang.axon.saga.ticket.event;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class OrderTicketPreservedEvent {

    private String ticketId;

    private String orderId;

    private String customerId;

    public OrderTicketPreservedEvent(String ticketId, String orderId, String customerId) {
        this.ticketId = ticketId;
        this.orderId = orderId;
        this.customerId = customerId;
    }
}

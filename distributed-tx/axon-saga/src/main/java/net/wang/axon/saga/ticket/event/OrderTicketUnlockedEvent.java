package net.wang.axon.saga.ticket.event;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * 出错了解锁票
 */
@Data
public class OrderTicketUnlockedEvent {

    private String ticketId;

    private String customerId;

    public OrderTicketUnlockedEvent(String ticketId) {
        this.ticketId = ticketId;
    }
}

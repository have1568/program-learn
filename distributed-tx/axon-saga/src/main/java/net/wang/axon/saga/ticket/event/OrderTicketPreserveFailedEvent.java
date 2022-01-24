package net.wang.axon.saga.ticket.event;

import lombok.Data;

@Data
public class OrderTicketPreserveFailedEvent {

    private String orderId;

    public OrderTicketPreserveFailedEvent(String orderId) {
        this.orderId = orderId;
    }
}

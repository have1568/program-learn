package net.wang.axon.saga.order.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class OrderCreatedEvent {

    private String orderId;

    private String customerId;

    private String ticketId;

    private Double amount;

    private String title;

    private ZonedDateTime createAt;

    public OrderCreatedEvent(String orderId, String customerId, String ticketId, Double amount, String title, ZonedDateTime createAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.title = title;
        this.createAt = createAt;
    }
}

package net.wang.axon.saga.order.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class OrderFinishedEvent {

    private String orderId;

    public OrderFinishedEvent(String orderId) {
        this.orderId = orderId;
    }
}

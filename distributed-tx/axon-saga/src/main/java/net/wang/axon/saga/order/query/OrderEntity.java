package net.wang.axon.saga.order.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.wang.axon.saga.order.OrderStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity(name = "o_order")
public class OrderEntity {

    @Id
    private String orderId;

    private String title;

    private String ticketId;

    private String customerId;

    private Double amount;

    private String reason;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    private ZonedDateTime createAt;


}

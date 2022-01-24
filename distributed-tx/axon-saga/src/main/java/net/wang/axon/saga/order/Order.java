package net.wang.axon.saga.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.order.command.OrderCreateCommand;
import net.wang.axon.saga.order.command.OrderFailCommand;
import net.wang.axon.saga.order.command.OrderFinishCommand;
import net.wang.axon.saga.order.event.OrderCreatedEvent;
import net.wang.axon.saga.order.event.OrderFailedEvent;
import net.wang.axon.saga.order.event.OrderFinishedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.ZonedDateTime;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Slf4j
@Data
@NoArgsConstructor
@Aggregate
public class Order {

    @AggregateIdentifier
    private String orderId;

    private String title;

    private String ticketId;

    private String customerId;

    private Double amount;

    private String reason;

    private OrderStatus status;

    private ZonedDateTime createAt;


    @CommandHandler
    public Order(OrderCreateCommand command) {
        log.info("command---> 创建订单命令 \tcommand = {}", command);
        apply(new OrderCreatedEvent(command.getOrderId(), command.getCustomerId(), command.getTicketId(), command.getAmount(), command.getTitle(), ZonedDateTime.now()));
    }

    @CommandHandler
    public void handle(OrderFinishCommand command) {
        log.info("command---> 订单完成命令 \tcommand = {}", command);
        apply(new OrderFinishedEvent(command.getOrderId()));
    }


    @CommandHandler
    public void handle(OrderFailCommand command) {
        log.info("command---> 订单失败命令 \tcommand = {}", command);
        apply(new OrderFailedEvent(command.getOrderId(), command.getReason()));
    }


    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        log.info("event---> 创建订单事件 \tevent = {}", event);
        this.orderId = event.getOrderId();
        this.title = event.getTitle();
        this.ticketId = event.getTicketId();
        this.customerId = event.getCustomerId();
        this.amount = event.getAmount();
        this.status = OrderStatus.NEW;
        this.createAt = event.getCreateAt();
    }

    @EventSourcingHandler
    public void on(OrderFinishedEvent event) {
        log.info("event---> 订单完成事件 \tevent = {}", event);
        this.orderId = event.getOrderId();
        this.status = OrderStatus.SUCCESS;
    }

    @EventSourcingHandler
    public void on(OrderFailedEvent event) {
        log.info("event---> 订单失败事件 \tevent = {}", event);
        this.orderId = event.getOrderId();
        this.reason = event.getReason();
        this.status = OrderStatus.FAIL;
    }


}

package net.wang.axon.saga.ticket;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.ticket.command.OrderTicketMoveCommand;
import net.wang.axon.saga.ticket.command.OrderTicketPreserveCommand;
import net.wang.axon.saga.ticket.command.OrderTicketUnlockCommand;
import net.wang.axon.saga.ticket.command.TicketCreateCommand;
import net.wang.axon.saga.ticket.event.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Slf4j
@Data
@NoArgsConstructor
@Aggregate
public class Ticket {

    @AggregateIdentifier
    private String ticketId;

    private String name;

    private String lockUser;

    private String owner;


    @CommandHandler
    public Ticket(TicketCreateCommand command) {
        log.info("command---> 创建票命令 ");
        apply(new TicketCreatedEvent(command.getTicketId(), command.getName()));
    }

    @CommandHandler
    public void handle(OrderTicketPreserveCommand command) {
        log.info("command---> 锁票命令 \tcommand = {} ", command);
        if (this.owner != null) {
            log.error("command---> 票已被转移 !");
            apply(new OrderTicketPreserveFailedEvent(command.getOrderId()));
            return;
        }
        if (this.lockUser != null) {
            if (this.lockUser.equals(command.getCustomerId())) {
                log.info("command---> 当前用户已经锁票 !");
            } else {
                log.info("command---> 票已被其他用户锁住 ! \tlockUser = {}", this.lockUser);
                apply(new OrderTicketPreserveFailedEvent(command.getOrderId()));
            }
        } else {
            log.info("command---> 执行锁票事件");
            apply(new OrderTicketPreservedEvent(command.getTicketId(), command.getOrderId(), command.getCustomerId()));
        }

    }


    @CommandHandler
    public void handle(OrderTicketMoveCommand command) {
        log.error("command---> 执行交票命令 ! \tcommand = {}", command);
        if (this.lockUser == null) {
            log.error("command---> 交票时票没有被锁 !");
        } else if (!this.lockUser.equals(command.getCustomerId())) {
            log.error("command---> 交票时票被其他用户锁住 !");
        } else {
            apply(new OrderTicketMovedEvent(command.getTicketId(), command.getOrderId(), command.getCustomerId()));
        }
    }

    @CommandHandler
    public void handle(OrderTicketUnlockCommand command) {
        log.error("command---> 执行解锁票命令 ! \tcommand = {}", command);
        if (this.lockUser == null) {
            log.error("command---> 解锁票时没有用户锁票 !");
        } else if (!this.lockUser.equals(command.getCustomerId())) {
            log.error("command---> 解锁票时被其他用户锁票 !");
        } else {
            apply(new OrderTicketUnlockedEvent(command.getTicketId()));
        }
    }


    @EventSourcingHandler
    public void on(TicketCreatedEvent event) {
        log.info("event---> 创建票事件  \tevent = {}", event);
        this.ticketId = event.getTicketId();
        this.name = event.getName();
    }

    @EventSourcingHandler
    public void on(OrderTicketPreservedEvent event) {
        log.info("event---> 锁票事件  \tevent = {}", event);
        this.lockUser = event.getCustomerId();
    }

    @EventSourcingHandler
    public void on(OrderTicketMovedEvent event) {
        log.info("event---> 交票事件  \tevent = {}", event);
        this.lockUser = null;
        this.owner = event.getCustomerId();
    }

    @EventSourcingHandler
    public void on(OrderTicketUnlockedEvent event) {
        log.info("event---> 解锁票事件  \tevent = {}", event);
        this.lockUser = null;

    }


}

package net.wang.axon.saga.customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.customer.command.CustomerChargeCommand;
import net.wang.axon.saga.customer.command.CustomerCreateCommand;
import net.wang.axon.saga.customer.command.CustomerDepositCommand;
import net.wang.axon.saga.customer.command.OrderPayCommand;
import net.wang.axon.saga.customer.event.CustomerChargedEvent;
import net.wang.axon.saga.customer.event.CustomerCreatedEvent;
import net.wang.axon.saga.customer.event.CustomerDepositedEvent;
import net.wang.axon.saga.customer.event.OrderPaidEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Customer聚合对象
 */
@Slf4j
@Data
@NoArgsConstructor
@Aggregate  //聚合对象注解
public class Customer {

    @AggregateIdentifier  //聚合对象ID注解
    private String customerId;

    private String username;

    private String password;

    private Double deposit;

    /**
     * 用户创建聚合对象命令
     * 命令将接口的参数封装后，通过AggregateLifecycle.apply()方法派发事件并将参数进一步传给对应的事件
     *
     * @param command
     */
    @CommandHandler
    public Customer(CustomerCreateCommand command) {
        log.info("command--->【用户创建聚合对象】命令\t command = {}", command);
        apply(new CustomerCreatedEvent(command.getCustomerId(), command.getUsername(), command.getPassword(), 0D));
    }

    /**
     * 用户存款命令
     *
     * @param command
     */
    @CommandHandler
    private void handle(CustomerDepositCommand command) {
        log.info("command--->【用户存款】命令\tcommand = {}", command);
        apply(new CustomerDepositedEvent(command.getCustomerId(), command.getAmount()));
    }

    /**
     * 用户消费命令
     *
     * @param command
     */
    @CommandHandler
    private void handle(CustomerChargeCommand command) {
        if (deposit < command.getAmount()) throw new IllegalArgumentException("余额不足");
        log.info("command--->【用户消费】命令-\tcommand = {}", command);
        apply(new CustomerChargedEvent(command.getCustomerId(), command.getAmount()));
    }

    /**
     * 用户支付命令
     *
     * @param command
     */
    @CommandHandler
    private void handle(OrderPayCommand command) {
        if (command.getAmount() == 0) {
            log.warn("command---> do nothing");
            return;
        }
        if (this.deposit < command.getAmount()) throw new IllegalArgumentException("余额不足");
        log.info("command--->【用户支付】命令\t command = {}", command);
        apply(new OrderPaidEvent(command.getOrderId(), command.getCustomerId(), command.getAmount()));
    }

    /**
     * 用户创建聚合对象事件
     * 在用户创建聚合对象命令{@link Customer#Customer(net.wang.axon.saga.customer.command.CustomerCreateCommand)}执行AggregateLifecycle.apply(Event)后，会立即执行此事件
     *
     * @param event
     */
    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
        log.info("event--->【用户创建聚合对象】事件 event = {}", event);
        this.customerId = event.getCustomerId();
        this.username = event.getUsername();
        this.password = event.getPassword();
        this.deposit = event.getDeposit();
    }

    /**
     * 用户存款事件
     *
     * @param event
     */
    @EventSourcingHandler
    public void on(CustomerDepositedEvent event) {
        log.info("event--->【用户存款】事件 event = {}", event);
        this.deposit += event.getAmount();
    }

    /**
     * 用户消费事件
     *
     * @param event
     */
    @EventSourcingHandler
    public void on(CustomerChargedEvent event) {
        log.info("event--->【用户取款】事件 event = {}", event);
        this.deposit -= event.getAmount();
    }

    /**
     * 用户支付事件
     *
     * @param event
     */
    @EventSourcingHandler
    public void on(OrderPaidEvent event) {
        log.info("event--->【用户支付】事件 event = {}", event);
        this.deposit -= event.getAmount();
    }
}

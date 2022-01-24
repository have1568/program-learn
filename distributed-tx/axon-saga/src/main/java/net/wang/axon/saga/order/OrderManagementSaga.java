package net.wang.axon.saga.order;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.customer.command.OrderPayCommand;
import net.wang.axon.saga.customer.event.OrderPaidEvent;
import net.wang.axon.saga.customer.event.OrderPayFailedEvent;
import net.wang.axon.saga.order.command.OrderFailCommand;
import net.wang.axon.saga.order.command.OrderFinishCommand;
import net.wang.axon.saga.order.event.OrderCreatedEvent;
import net.wang.axon.saga.order.event.OrderFailedEvent;
import net.wang.axon.saga.order.event.OrderFinishedEvent;
import net.wang.axon.saga.ticket.command.OrderTicketMoveCommand;
import net.wang.axon.saga.ticket.command.OrderTicketPreserveCommand;
import net.wang.axon.saga.ticket.command.OrderTicketUnlockCommand;
import net.wang.axon.saga.ticket.event.OrderTicketMovedEvent;
import net.wang.axon.saga.ticket.event.OrderTicketPreserveFailedEvent;
import net.wang.axon.saga.ticket.event.OrderTicketPreservedEvent;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.spring.stereotype.Saga;

import javax.annotation.Resource;
import java.time.Instant;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

/**
 * Saga流程执行完成后会删除当前事件
 * association_value_entry
 * saga_entry
 * 如果没有执行完成事件，事件将会被保存在上面的表里
 */
@Slf4j
@Saga
@Data
public class OrderManagementSaga {

    @Resource
    private transient CommandBus commandBus;  //不序列化
    @Resource
    private transient EventScheduler eventScheduler;
    private String orderId;
    private String ticketId;
    private String customerId;
    private Double amount;
    private ScheduleToken scheduleToken;

    /**
     * 创建订单成功，执行锁票命令
     *
     * @param event 从创建订单事件开始Saga事件流程
     */
    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")  //关联属性是orderId
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.ticketId = event.getTicketId();
        this.customerId = event.getCustomerId();
        this.amount = event.getAmount();

        scheduleToken = eventScheduler.schedule(Instant.now().plusSeconds(30), new OrderFailedEvent(orderId, "TimeOut"));

        log.info("Saga--->创建订单成功，执行锁票命令\t event : {}", event);
        OrderTicketPreserveCommand command = new OrderTicketPreserveCommand(event.getTicketId(), event.getOrderId(), event.getCustomerId());
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
        log.info("Saga--->锁票命令派发完成\t event : {}", event);
    }

    /**
     * 锁票成功，执行订单支付命令
     *
     * @param event
     */
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketPreservedEvent event) {
        log.info("Saga--->锁票成功，执行订单支付命令\t event : {}", event);
        OrderPayCommand command = new OrderPayCommand(this.orderId, this.customerId, this.amount);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    /**
     * 锁票失败，执行订单失败命令
     *
     * @param event
     */
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketPreserveFailedEvent event) {
        log.info("Saga--->锁票失败，执行订单失败命令\t event : {}", event);
        OrderFailCommand command = new OrderFailCommand(this.orderId, "锁票失败");
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    /**
     * 订单支付成功，执行交票命令
     *
     * @param event
     */
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPaidEvent event) {
        log.info("Saga--->订单支付成功，执行交票命令\t event : {}", event);
        OrderTicketMoveCommand command = new OrderTicketMoveCommand(this.ticketId, this.orderId, this.customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }


    /**
     * 订单支付失败，执行解锁票和订单失败命令
     *
     * @param event
     */
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPayFailedEvent event) {
        log.info("Saga--->订单支付失败，执行解锁票和订单失败命令\t event : {}", event);

        OrderTicketUnlockCommand command = new OrderTicketUnlockCommand(this.ticketId, this.customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);

        OrderFailCommand orderFailCommand = new OrderFailCommand(this.orderId, "支付失败");
        commandBus.dispatch(asCommandMessage(orderFailCommand), LoggingCallback.INSTANCE);
    }

    /**
     * 交票成功，执行订单完成命令
     *
     * @param event
     */
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketMovedEvent event) {
        log.info("Saga--->交票成功，执行订单完成命令\t event : {}", event);
        OrderFinishCommand command = new OrderFinishCommand(this.orderId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderFailedEvent event) {
        log.info("EndSaga--->order fail\tevent = {}", event);
        if(this.scheduleToken !=null){
            log.info("EndSaga--->order failed,cancel scheduleToken");
            eventScheduler.cancelSchedule(this.scheduleToken);
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderFinishedEvent event) {
        log.info("EndSaga--->order finish\tevent = {}", event);
        if(this.scheduleToken !=null){
            log.info("EndSaga--->order finished,cancel scheduleToken");
            eventScheduler.cancelSchedule(this.scheduleToken);
        }
    }
}

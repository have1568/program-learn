package net.wang.axon.saga.order.query;

import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.order.OrderStatus;
import net.wang.axon.saga.order.event.OrderCreatedEvent;
import net.wang.axon.saga.order.event.OrderFailedEvent;
import net.wang.axon.saga.order.event.OrderFinishedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class OrderEntityProjector {

    @Resource
    private OrderEntityRepo orderEntityRepo;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity entity = new OrderEntity();
        log.info("Projector--->  order create projector\tevent = {}", event);
        entity.setOrderId(event.getOrderId());
        entity.setTitle(event.getTitle());
        entity.setTicketId(event.getTicketId());
        entity.setCustomerId(event.getCustomerId());
        entity.setAmount(event.getAmount());
        entity.setStatus(OrderStatus.NEW);
        entity.setCreateAt(event.getCreateAt());
        orderEntityRepo.save(entity);
    }

    @EventHandler
    public void on(OrderFinishedEvent event) {
        OrderEntity entity = orderEntityRepo.findOne(event.getOrderId());
        log.info("Projector--->  order finish projector\tevent = {}", event);
        entity.setOrderId(event.getOrderId());
        entity.setStatus(OrderStatus.SUCCESS);
        orderEntityRepo.save(entity);

    }

    @EventHandler
    public void on(OrderFailedEvent event) {
        OrderEntity entity = orderEntityRepo.findOne(event.getOrderId());
        log.info("Projector--->  order fail projector\tevent = {}", event);
        entity.setOrderId(event.getOrderId());
        entity.setReason(event.getReason());
        entity.setStatus(OrderStatus.FAIL);
        orderEntityRepo.save(entity);
    }
}

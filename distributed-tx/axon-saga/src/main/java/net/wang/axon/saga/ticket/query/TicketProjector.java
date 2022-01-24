package net.wang.axon.saga.ticket.query;

import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.ticket.event.OrderTicketMovedEvent;
import net.wang.axon.saga.ticket.event.OrderTicketPreservedEvent;
import net.wang.axon.saga.ticket.event.OrderTicketUnlockedEvent;
import net.wang.axon.saga.ticket.event.TicketCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class TicketProjector {

    @Resource
    private TicketEntityRepo ticketEntityRepo;

    @EventHandler
    public void on(TicketCreatedEvent event) {
        log.info("Projector---> TicketCreatedEvent Projector  \tevent = {}", event);
        TicketEntity entity = new TicketEntity();
        entity.setTicketId(event.getTicketId());
        entity.setName(event.getName());
        entity.setOwner(null);
        entity.setLockUser(null);
        ticketEntityRepo.save(entity);
    }

    @EventHandler
    public void on(OrderTicketPreservedEvent event) {
        log.info("Projector--->  OrderTicketPreservedEvent Projector  \tevent = {}", event);
        TicketEntity entity = ticketEntityRepo.findOne(event.getTicketId());
        entity.setLockUser(event.getCustomerId());
        ticketEntityRepo.save(entity);

    }

    @EventHandler
    public void on(OrderTicketMovedEvent event) {
        log.info("Projector--->  OrderTicketMovedEvent Projector  \tevent = {}", event);
        TicketEntity entity = ticketEntityRepo.findOne(event.getTicketId());
        entity.setLockUser(null);
        entity.setOwner(event.getCustomerId());
        ticketEntityRepo.save(entity);
    }

    @EventHandler
    public void on(OrderTicketUnlockedEvent event) {
        log.info("Projector--->  OrderTicketMovedEvent Projector  \tevent = {}", event);
        TicketEntity entity = ticketEntityRepo.findOne(event.getTicketId());
        entity.setLockUser(null);
        ticketEntityRepo.save(entity);
    }

}

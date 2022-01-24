package net.wang.axon.saga.ticket.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.ticket.command.OrderTicketMoveCommand;
import net.wang.axon.saga.ticket.command.OrderTicketPreserveCommand;
import net.wang.axon.saga.ticket.command.TicketCreateCommand;
import net.wang.axon.saga.ticket.event.OrderTicketMovedEvent;
import net.wang.axon.saga.ticket.event.OrderTicketPreserveFailedEvent;
import net.wang.axon.saga.ticket.event.OrderTicketPreservedEvent;
import net.wang.axon.saga.ticket.event.TicketCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Data
@NoArgsConstructor
@Entity(name = "t_ticket")
public class TicketEntity {

    @Id
    private String ticketId;

    private String name;

    private String lockUser;

    private String owner;

}

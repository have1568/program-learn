package net.wang.axon.saga.ticket.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketEntityRepo extends JpaRepository<TicketEntity,String> {
}

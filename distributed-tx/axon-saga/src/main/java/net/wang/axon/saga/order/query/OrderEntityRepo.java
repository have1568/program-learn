package net.wang.axon.saga.order.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEntityRepo extends JpaRepository<OrderEntity,String> {
}

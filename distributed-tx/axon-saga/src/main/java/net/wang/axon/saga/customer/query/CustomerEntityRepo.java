package net.wang.axon.saga.customer.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerEntityRepo extends JpaRepository<CustomerEntity,String> {
}

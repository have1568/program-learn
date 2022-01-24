package net.wang.axon.saga.order.query;

import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.order.Order;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 查询order
 */
@Slf4j
@Component
public class OrderQueryHandler {

    @Resource
    private AxonConfiguration axonConfiguration;
    @Resource
    private OrderEntityRepo orderEntityRepo;

    @QueryHandler
    public Order query(OrderId orderId) {
        final Order[] orders = new Order[1];
        Repository<Order> repository = axonConfiguration.repository(Order.class);
        log.info("orderId = {}", orderId);
        repository.load(orderId.getIdentifier()).execute(order -> orders[0] = order);
        return orders[0];
    }
    /**
     * 通过物化视图查询，2个QueryHandler的参数不能一样
     */
//
//    @QueryHandler
//    public OrderEntity queryByView(OrderId orderId) {
//        return orderEntityRepo.findOne(orderId.toString());
//    }
}

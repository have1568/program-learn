package net.wang.axon.saga.customer.query;

import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.customer.event.CustomerChargedEvent;
import net.wang.axon.saga.customer.event.CustomerCreatedEvent;
import net.wang.axon.saga.customer.event.CustomerDepositedEvent;
import net.wang.axon.saga.customer.event.OrderPaidEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 将聚合对象投影到物化视图上，保持两者一致，同步实在同一个事务下执行的
 */
@Slf4j
@Component
public class CustomerProjector {

    @Resource
    private CustomerEntityRepo customerEntityRepo;

    /**
     * 创建聚合对象完成后执行投影到物化视图
     * <p>
     * 执行完成用户创建聚合对象事件后{@link net.wang.axon.saga.customer.Customer#on(net.wang.axon.saga.customer.event.CustomerCreatedEvent)}
     * 将事件传递到投影聚合对象到物化视图并保存到数据库
     * </p>
     *
     * @param event
     */
    @EventHandler
    public void on(CustomerCreatedEvent event) {
        log.info("Projector--->**创建聚合对象**完成后执行投影到物化视图\tevent = {}", event);
        customerEntityRepo.save(new CustomerEntity(event.getCustomerId(), event.getUsername(), event.getPassword(), event.getDeposit()));
    }

    /**
     * 用户存款事件完成后执行投影到物化视图
     *
     * @param event
     */
    @EventHandler
    public void on(CustomerDepositedEvent event) {
        log.info("Projector--->**用户存款事件**完成后执行投影到物化视图\tevent = {}", event);
        CustomerEntity customerEntity = customerEntityRepo.findOne(event.getCustomerId());
        if (customerEntity == null) throw new RuntimeException("用户不存在，存款失败");
        customerEntity.setDeposit(customerEntity.getDeposit() + event.getAmount());
        customerEntityRepo.save(customerEntity);
    }

    /**
     * 用户消费事件完成后执行投影到物化视图
     *
     * @param event
     */
    @EventHandler
    public void on(CustomerChargedEvent event) {
        log.info("Projector--->**用户消费事件**完成后执行投影到物化视图\tevent = {}", event);
        CustomerEntity customerEntity = customerEntityRepo.findOne(event.getCustomerId());
        if (customerEntity == null) throw new RuntimeException("用户不存在，存款失败");
        customerEntity.setDeposit(customerEntity.getDeposit() - event.getAmount());
        customerEntityRepo.save(customerEntity);
    }


    /**
     * 用户支付事件完成后执行投影到物化视图
     *
     * @param event
     */
    @EventHandler
    public void on(OrderPaidEvent event) {
        log.info("Projector--->**用户支付事件**完成后执行投影到物化视图\tevent = {}", event);
        CustomerEntity customerEntity = customerEntityRepo.findOne(event.getCustomerId());
        if (customerEntity == null) throw new RuntimeException("用户不存在，存款失败");
        customerEntity.setDeposit(customerEntity.getDeposit() - event.getAmount());
        customerEntityRepo.save(customerEntity);
    }
}

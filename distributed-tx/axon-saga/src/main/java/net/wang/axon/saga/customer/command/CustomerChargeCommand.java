package net.wang.axon.saga.customer.command;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * 用户消费命令
 */
@Data
public class CustomerChargeCommand {

    @TargetAggregateIdentifier  //指向聚合对象的ID字段
    private String customerId;

    private Double amount; //消费金额

    public CustomerChargeCommand(String customerId, Double amount) {
        this.customerId = customerId;
        this.amount = amount;
    }
}

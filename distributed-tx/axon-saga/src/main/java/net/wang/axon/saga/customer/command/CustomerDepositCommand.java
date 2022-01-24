package net.wang.axon.saga.customer.command;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * 用户存款命令
 */
@Data
public class CustomerDepositCommand {

    @TargetAggregateIdentifier  //指向聚合对象的ID字段
    private String customerId;

    private Double amount; //存款金额

    public CustomerDepositCommand(String customerId, Double amount) {
        this.customerId = customerId;
        this.amount = amount;
    }
}

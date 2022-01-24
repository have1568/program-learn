package net.wang.axon.saga.customer.command;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * 创建用户命令
 */
@Data
public class CustomerCreateCommand {

    @TargetAggregateIdentifier  //指向聚合对象的ID字段
    private String customerId;

    private String username;

    private String password;

    private Double deposit;

    public CustomerCreateCommand(String customerId, String username, String password, Double deposit) {
        this.customerId = customerId;
        this.username = username;
        this.password = password;
        this.deposit = deposit;
    }
}

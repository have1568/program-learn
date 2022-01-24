package net.wang.axon.account;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wang.axon.account.command.AccountCreateCommand;
import net.wang.axon.account.command.AccountDepositCommand;
import net.wang.axon.account.command.AccountWithdrawCommand;
import net.wang.axon.account.event.AccountCreatedEvent;
import net.wang.axon.account.event.AccountDepositedEvent;
import net.wang.axon.account.event.AccountWithDrawedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Data
@Aggregate
@NoArgsConstructor
@Slf4j
public class Account {

    @AggregateIdentifier
    private String accountId;

    private Double deposit;


    /**
     * 创建账户命令
     *
     * @param command
     */
    @CommandHandler
    public Account(AccountCreateCommand command) {
        log.info("账户【创建】命令 command = {}", command);
        apply(new AccountCreatedEvent(command.getAccountId()));
    }

    /**
     * 账户存款命令
     *
     * @param command
     */
    @CommandHandler
    public void deposit(AccountDepositCommand command) {
        log.info("账户【存款】命令 command = {}", command);
        apply(new AccountDepositedEvent(command.getAccountId(), command.getAmount()));
    }

    /**
     * 账户取款命令
     *
     * @param command
     */
    @CommandHandler
    public void withdraw(AccountWithdrawCommand command) {
        log.info("账户【取款】命令 command = {}", command);
        if (this.deposit >= command.getAmount()) {  //判断余额
            apply(new AccountWithDrawedEvent(command.getAccountId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("余额不足");
        }
    }


    /**
     * 创建账户事件处理器
     *
     * @param event
     */
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("账户【创建】事件处理器 event = {}", event);
        this.accountId = event.getAccountId();
        this.deposit = 0D;
    }

    /**
     * 账户存款事件处理器
     *
     * @param event
     */
    @EventSourcingHandler
    public void on(AccountDepositedEvent event) {
        log.info("账户【存款】事件处理器 event = {}", event);
        //this.accountId = event.getAccountId();
        this.deposit += event.getAmount();
    }

    /**
     * 账户存款事件处理器
     *
     * @param event
     */
    @EventSourcingHandler
    public void on(AccountWithDrawedEvent event) {
        log.info("账户【取款】事件处理器 event = {}", event);
        this.deposit -= event.getAmount();

    }

    public Account(String accountId, Double deposit) {
        this.accountId = accountId;
        this.deposit = deposit;
    }
}

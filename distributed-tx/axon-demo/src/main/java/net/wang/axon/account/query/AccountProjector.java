package net.wang.axon.account.query;

import lombok.extern.slf4j.Slf4j;
import net.wang.axon.account.event.AccountCreatedEvent;
import net.wang.axon.account.event.AccountDepositedEvent;
import net.wang.axon.account.event.AccountWithDrawedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 投影  将领域模型和数据库对象分开
 * 将聚合对象投影到物化视图上
 */
@Slf4j
@Component
public class AccountProjector {
    @Resource
    private AccountEntityRepo accountEntityRepo;


    /**
     * 在AccountCreatedEvent执行完成之后执行，并且2步在一个事务里执行
     *
     * @param event
     */
    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("==[创建对象]==保存物化模型 event = {}", event);
        accountEntityRepo.save(new AccountEntity(event.getAccountId()));
    }

    /**
     * 在AccountDepositedEvent执行完成之后执行，并且2步在一个事务里执行
     *
     * @param event
     */
    @EventHandler
    public void on(AccountDepositedEvent event) {
        log.info("==[存款]==保存物化模型 event = {}", event);
        AccountEntity accountEntity = accountEntityRepo.findOne(event.getAccountId());
        accountEntity.setDeposit(accountEntity.getDeposit() + event.getAmount());
        accountEntityRepo.save(accountEntity);
    }

    /**
     * 在AccountWithDrawedEvent执行完成之后执行，并且2步在一个事务里执行
     *
     * @param event
     */
    @EventHandler
    public void on(AccountWithDrawedEvent event) {
        log.info("==[取款]==保存物化模型 event = {}", event);
        AccountEntity accountEntity = accountEntityRepo.findOne(event.getAccountId());
        accountEntity.setDeposit(accountEntity.getDeposit() - event.getAmount());
        accountEntityRepo.save(accountEntity);
    }
}

package net.wang.axon.account;

import net.wang.axon.account.command.AccountCreateCommand;
import net.wang.axon.account.command.AccountDepositCommand;
import net.wang.axon.account.command.AccountWithdrawCommand;
import net.wang.axon.account.query.AccountEntity;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    @Resource
    private CommandGateway commandGateway;

    @PostMapping("")  //rest规范，创建mapping 为空
    public CompletableFuture<Object> create() {
        String s = UUID.randomUUID().toString();
        return commandGateway.send(new AccountCreateCommand(s));
    }


    @PutMapping(value = "/{accountId}/deposit/{amount}")
    public CompletableFuture<Object> deposit(@PathVariable String accountId, @PathVariable Double amount) {
        return commandGateway.send(new AccountDepositCommand(accountId, amount));
    }

    @PutMapping(value = "/{accountId}/withdraw/{amount}")
    public CompletableFuture<Object> withdraw(@PathVariable String accountId, @PathVariable Double amount) {
        return commandGateway.send(new AccountWithdrawCommand(accountId, amount));
    }
}

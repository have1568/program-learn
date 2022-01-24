package net.wang.axon.saga.customer;

import net.wang.axon.saga.customer.command.CustomerChargeCommand;
import net.wang.axon.saga.customer.command.CustomerCreateCommand;
import net.wang.axon.saga.customer.command.CustomerDepositCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {

    @Resource
    private CommandGateway commandGateway;  //命令网关通道，用于执行命令

    @PostMapping(value = "")
    public CompletableFuture<Object> create(@RequestParam String username,
                                            @RequestParam String password) {
        String customerId = UUID.randomUUID().toString();
        return commandGateway.send(new CustomerCreateCommand(customerId, username, password, 0D));
    }

    @PutMapping(value = "/{accountId}/deposit/{amount}")
    public CompletableFuture<Object> deposit(@PathVariable String accountId, @PathVariable Double amount) {
        return commandGateway.send(new CustomerDepositCommand(accountId, amount));
    }

    @PutMapping(value = "/{accountId}/withdraw/{amount}")
    public CompletableFuture<Object> withdraw(@PathVariable String accountId, @PathVariable Double amount) {
        return commandGateway.send(new CustomerChargeCommand(accountId, amount));
    }
}

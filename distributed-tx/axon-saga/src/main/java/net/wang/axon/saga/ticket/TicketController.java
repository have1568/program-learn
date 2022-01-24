package net.wang.axon.saga.ticket;

import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.ticket.command.TicketCreateCommand;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api/ticket")
public class TicketController {

    @Resource
    private CommandGateway commandGateway;

    @PostMapping(value = "")
    public void create(@RequestParam String name) {
        commandGateway.send(new TicketCreateCommand(UUID.randomUUID().toString(), name), LoggingCallback.INSTANCE);
    }
}

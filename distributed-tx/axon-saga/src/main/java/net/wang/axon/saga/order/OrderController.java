package net.wang.axon.saga.order;

import lombok.extern.slf4j.Slf4j;
import net.wang.axon.saga.order.command.OrderCreateCommand;
import net.wang.axon.saga.order.query.OrderId;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    @Resource
    private CommandGateway commandGateway;
    @Resource
    private QueryGateway queryGateway;

    @PostMapping(value = "")
    public void create(@RequestParam String customerId,
                       @RequestParam String ticketId,
                       @RequestParam Double amount,
                       @RequestParam String title) {
        commandGateway.send(new OrderCreateCommand(UUID.randomUUID().toString(), customerId, ticketId, amount, title), LoggingCallback.INSTANCE);
    }

    @GetMapping(value = "/query/{orderId}")
    public CompletableFuture<Order> getFromRepo(@PathVariable String orderId) {
        return queryGateway.query(new OrderId(orderId), Order.class);
    }
}

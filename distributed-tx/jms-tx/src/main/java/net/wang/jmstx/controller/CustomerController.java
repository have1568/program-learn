package net.wang.jmstx.controller;

import net.wang.jmstx.service.CustomerService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "api/jms/")
public class CustomerController {

    @Resource
    private CustomerService customerService;
    @Resource
    private JmsTemplate jmsTemplate;

    @PostMapping(value = "handle")
    public void handle(@RequestParam String msg) {    //直接向队列里写入消息
        jmsTemplate.convertAndSend("customer:msg1:new", "without handle == " + msg);
    }

    @PostMapping(value = "redirect")
    public void redirect(@RequestParam String msg) {
        customerService.handle(msg); //JmsListener 注解会在没有异常的才会提交事务
    }

    @PostMapping(value = "handleInCode")
    public void handleInCode(@RequestParam String msg) {
        customerService.handleInCode(msg); //JmsListener 注解会在没有异常的才会提交事务
    }

    @GetMapping(value = "read")
    public Object read() {
        jmsTemplate.setReceiveTimeout(2000);
        return jmsTemplate.receiveAndConvert("customer:msg:reply");
    }

}

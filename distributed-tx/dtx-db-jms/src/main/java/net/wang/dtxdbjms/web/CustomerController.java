package net.wang.dtxdbjms.web;

import net.wang.dtxdbjms.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @PostMapping(value = "/order")
    public void create(@RequestParam String storeName, @RequestParam int orderAmount) {
        customerService.createOrder(storeName, orderAmount);
    }
}

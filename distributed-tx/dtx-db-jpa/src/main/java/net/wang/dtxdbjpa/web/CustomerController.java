package net.wang.dtxdbjpa.web;

import net.wang.dtxdbjpa.domain.Order;
import net.wang.dtxdbjpa.service.CustomerService;
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
    public Order create(@RequestParam String storeName, @RequestParam int orderAmount) {
        return customerService.createOrder(storeName, orderAmount);
    }
}

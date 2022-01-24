package net.wang.dtxdbdb.web;

import net.wang.dtxdbdb.domain.Order;
import net.wang.dtxdbdb.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @PostMapping(value = "/order")
    public Order create(@RequestParam String storeName, @RequestParam int orderAmount) {
        return customerService.createOrder(storeName, orderAmount);
    }

    @GetMapping(value = "/{id}")
    public Map<String,Object> info(@PathVariable Long id){
        return customerService.getInfo(id);
    }
}

package net.wang.jtatx.web;

import net.wang.jtatx.domain.Customer;
import net.wang.jtatx.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "customer")
public class CustomerController {

    @Resource
    CustomerService customerService;

    @PostMapping(value = "/save")
    public Object save() {
        return customerService.save();
    }

    @GetMapping(value = "/all")
    public List<Customer> findAll(){
        return customerService.all();
    }

}

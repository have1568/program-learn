package net.wang.jtatx.service;

import net.wang.jtatx.domain.Customer;
import net.wang.jtatx.repo.CustomerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerService {

    @Resource
    private CustomerRepo customerRepo;

    @Transactional
    public Customer save() {
        Customer c = new Customer();
        System.out.println("Repo Save Start");
        Customer customer = customerRepo.save(new Customer("wang", "123456"));
        System.out.println("Repo Save End");
        if (true) {
            throw new RuntimeException("aaa");
        } else {
            return customer;
        }
    }

    public List<Customer> all() {
        return customerRepo.findAll();
    }
}

package net.wang.dtxdbjms.service;


import lombok.extern.slf4j.Slf4j;
import net.wang.dtxdbjms.domain.Customer;
import net.wang.dtxdbjms.repo.CustomerRepo;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class CustomerService {

    @Resource
    private CustomerRepo customerRepo;
    @Resource
    private JmsTemplate jmsTemplate;

    /**
     * 保存用户的同时想队列里发送一条消息
     *
     * @param storeName
     * @param orderAmount
     */
    @Transactional
    public void createOrder(String storeName, int orderAmount) {
        customerRepo.save(new Customer("wang", "66666"));
        if (orderAmount == 100) {
            throw new RuntimeException();
        }
        jmsTemplate.convertAndSend("customer:msg:new", "SUCCESS");
        if (orderAmount == 200) {
            throw new RuntimeException();
        }
    }

    /**
     * 监听消息
     * 如果此方法在其他项目中既就可以处理
     *
     * @param msg
     */
    @Transactional
    @JmsListener(destination = "customer:msg:new")
    public void handle(String msg) {
        log.info("====msg====  {}", msg);
        jmsTemplate.convertAndSend("customer:msg:replay", "replay");
        List<Customer> all = customerRepo.findAll();
        for (Customer customer : all) {
            customer.setName("replay");
        }
        customerRepo.save(all);
    }
}

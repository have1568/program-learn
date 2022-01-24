package net.wang.dtxdbjpa.service;


import net.wang.dtxdbjpa.domain.Customer;
import net.wang.dtxdbjpa.domain.Order;
import net.wang.dtxdbjpa.repo.CustomerRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class CustomerService {

    @Resource
    private CustomerRepo customerRepo;
    @Resource
    private JdbcTemplate orderJdbcTemplate;


    @Transactional
    public Order createOrder(String storeName, int orderAmount) {


        customerRepo.save(new Customer("wang", "555555"));
        if (orderAmount == 100) {
            throw new RuntimeException();
        }

        String orderSQL = "INSERT INTO `db_order`.`c_order`(`store_name`, `amount`, `create_at`) VALUES ('store1', 100, '2018-08-26 11:42:31')";
        orderJdbcTemplate.update(orderSQL); //出现错误不回滚
        if (orderAmount == 200) {
            throw new RuntimeException();
        }
        return null;
    }
}

package net.wang.dtxdbdb.service;

import net.wang.dtxdbdb.domain.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CustomerService {

    @Resource
    private JdbcTemplate userJdbcTemplate;
    @Resource
    private JdbcTemplate orderJdbcTemplate;


    @Transactional
    public Order createOrder(String storeName, int orderAmount) {
        String userSQL = "INSERT INTO `db_default`.`c_customer`(`name`, `password`, `create_at`) VALUES ('wang1', '123456', '2018-08-26 11:42:31')";
        String orderSQL = "INSERT INTO `db_order`.`c_order`(`store_name`, `amount`, `create_at`) VALUES ('store1', 100, '2018-08-26 11:42:31')";
        userJdbcTemplate.update(userSQL); //出现错误回滚
        if (orderAmount == 100) {
            throw new RuntimeException();
        }

        orderJdbcTemplate.update(orderSQL); //出现错误不回滚
        if (orderAmount == 200) {
            throw new RuntimeException();
        }
        return null;
    }

    public Map<String, Object> getInfo(Long id) {

        return null;
    }
}

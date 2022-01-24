package net.wang.jmstx.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class CustomerService {

    public static final org.slf4j.Logger log = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    PlatformTransactionManager transactionManager;

    @Transactional   //事务注解
    @JmsListener(destination = "customer:msg1:new", containerFactory = "containerFactory")  //监听目的地 containerFactory要和配置文件jmsTemplate的相同才能正常回滚
    public void handle(String msg) {
        log.info("msg = {}", msg);
        //将msg 加上replay == 前缀并发送到 customer:msg:reply
        jmsTemplate.convertAndSend("customer:msg:reply", "replay == " + msg);
        if (msg.contains("error")) {
            error();
        }
    }


    public void handleInCode(String msg) {
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(definition);
        log.info("msg = {}", msg);

        try {
            //将msg 加上replay == 前缀并发送到 customer:msg:reply
            jmsTemplate.convertAndSend("customer:msg:reply", "replay == " + msg);
            if (msg.contains("error")) {
                error();
            }
            transactionManager.commit(transaction);
        } catch (JmsException e) {
            e.printStackTrace();
            transactionManager.rollback(transaction);
        }
    }

    private void error() {
        throw new RuntimeException("some error");
    }



}

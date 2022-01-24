package net.wang.jmstx.service;

import net.wang.jmstx.JmsTxApplicationTests;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class CustomerServiceTest extends JmsTxApplicationTests {

    @Resource
    private CustomerService customerService;
    @Test
    public void handle() {
        customerService.handle("hello");
    }
}
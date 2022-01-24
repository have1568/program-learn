package net.wang.jms.consumer;

import net.wang.jms.config.DefaultConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.JMSException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class})
public class AppConsumerServiceTest {

    @Resource
    private AppConsumerService appConsumerService;

    @Test
    public void getMessage() throws JMSException {
        for (int i = 0; i < 100; i++) {
            appConsumerService.getMessage();
        }
    }
}
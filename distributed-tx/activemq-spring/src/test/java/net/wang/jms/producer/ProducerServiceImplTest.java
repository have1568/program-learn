package net.wang.jms.producer;

import net.wang.jms.config.DefaultConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class})
public class ProducerServiceImplTest {


    @Autowired
    ProducerService producerService;

    @Test
    public void sendMessage() {
        for (int i = 0; i < 100; i++) {
            producerService.sendMessage("hello" + i);
        }
    }


}
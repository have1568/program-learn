package net.wang.jms.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

@Service
public class ProducerServiceImpl implements ProducerService {

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination destination;

    @Override
    public void sendMessage(final String message) {
        //向目的地发送消息，消息是通过会话创建
        jmsTemplate.send(destination, session -> session.createTextMessage(message));
    }
}

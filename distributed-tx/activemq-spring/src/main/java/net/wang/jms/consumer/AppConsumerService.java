package net.wang.jms.consumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
public class AppConsumerService {

    @Resource
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination destination;

    public void getMessage() throws JMSException {
        TextMessage receive = (TextMessage) jmsTemplate.receive(destination);
        System.out.println(receive.getText());
    }
}

package net.wang.jms.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.*;


@Configuration
@ComponentScan(basePackages = "net.wang.jms")
public class DefaultConfig {
    public static final String URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "QUEUE_SPRING_TEST";

    //链接工厂
    @Bean
    public SingleConnectionFactory connectionFactory() {
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory();
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(URL);
        singleConnectionFactory.setTargetConnectionFactory(connectionFactory);
        return singleConnectionFactory;
    }

    //消息目标
    @Bean
    public Destination destination() {
        ActiveMQDestination destination = new ActiveMQQueue(QUEUE_NAME);  //可以直接修改为主题模式
        return destination;
    }

    //消息发送模板
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }


    //消息监听器
    @Bean
    public MessageListener messageListener() {
        return message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        };
    }

    //消息监听容器
    @Bean
    public DefaultMessageListenerContainer listenerContainer(Destination destination,
                                                             ConnectionFactory connectionFactory,
                                                             MessageListener messageListener) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestination(destination);
        container.setMessageListener(messageListener);
        return container;
    }
}

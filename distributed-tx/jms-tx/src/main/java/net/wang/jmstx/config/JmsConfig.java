package net.wang.jmstx.config;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

@EnableJms
@Configuration
public class JmsConfig {


    @Bean
    public PlatformTransactionManager transactionManager(ConnectionFactory factory) {
        return new JmsTransactionManager(factory);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory factory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(factory);
        return jmsTemplate;
    }

    @Bean
    public JmsListenerContainerFactory containerFactory(ConnectionFactory factory,
                                                  PlatformTransactionManager transactionManager,
                                                  DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        configurer.configure(containerFactory, factory);
        containerFactory.setReceiveTimeout(10000L);
        containerFactory.setTransactionManager(transactionManager);
        return containerFactory;
    }
}

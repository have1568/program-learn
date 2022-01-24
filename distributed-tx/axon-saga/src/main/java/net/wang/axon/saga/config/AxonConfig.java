package net.wang.axon.saga.config;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.java.SimpleEventScheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.axonframework.common.transaction.TransactionManager;
import java.util.concurrent.Executors;

@Configuration
public class AxonConfig {

    @Bean
    public EventScheduler eventScheduler(@Qualifier("eventBus") EventBus eventBus, TransactionManager transactionManager){
        return new SimpleEventScheduler(Executors.newScheduledThreadPool(1),eventBus, transactionManager);
    }
}

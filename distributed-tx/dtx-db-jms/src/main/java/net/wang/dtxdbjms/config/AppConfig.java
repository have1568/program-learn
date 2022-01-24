package net.wang.dtxdbjms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsAccessor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

@Configuration
public class AppConfig {

    @Resource
    ConnectionFactory jmsConnectionFactory;
    @Resource
    JmsTemplate jmsTemplate;

    /**
     * <p>
     * 初始化将消息中间件加入到本地事务管理器中
     * {@link TransactionAwareConnectionFactoryProxy#synchedLocalTransactionAllowed 默认值 false}
     * {@link JmsAccessor#sessionTransacted 默认值 false}
     * </P>
     * TODO 此处配置可以考虑将 JmsTransactionManager和其他种类的事务管理器加入一个链式事务管理器
     */
    @PostConstruct
    public void jmsTransConfigInit() {
        TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy();
        proxy.setTargetConnectionFactory(jmsConnectionFactory);
        proxy.setSynchedLocalTransactionAllowed(true); //将消息中间件事务发送消息加入到本地事务中

        jmsTemplate.setSessionTransacted(true); //开启jmsTemplate的事务机制，默认是关闭的
    }
}

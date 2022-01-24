package net.wang.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * 消息生产者
 */
public class AppProducer {
    public static final String URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "QUEUE_TEST";


    @Test
    public void test() throws JMSException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //打开链接
        connection.start();

        //创建会话  false 没有事务，应答模式? TODO
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建一个目标  队列消息
        Queue queue = session.createQueue(QUEUE_NAME);

        //创建生产者
        MessageProducer producer = session.createProducer(queue);

        for (int i = 0; i < 100; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("test" + i);
            //发送消息
            producer.send(textMessage);
            System.out.println(textMessage.getText());
        }
        //关闭链接
        connection.close();
    }

}

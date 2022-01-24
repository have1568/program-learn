package net.wang.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppConsumer {
    public static final String URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "QUEUE_TEST";

    public static void main(String[] args) throws JMSException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //打开链接
        connection.start();

        //创建会话  false 没有事务，应答模式? TODO
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建一个目标  队列消息
        Topic topic = session.createTopic(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
//        Thread.sleep(1000L);
//        connection.close();
    }
}

package com.sh;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @ClassName Consumer
 * @Desc
 * @Author leeshuhua
 * @Date 2020/4/12 17:21
 * @Version V1.0
 **/
public class Consumer {

    public static void main(String[] args) {

        ActiveMQConnectionFactory connectionFactory = null;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;

        try {
            // 1、创建连接工厂（不需要手动指定，自动发现）
            connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://123.207.255.168:61616");
            // 2、创建连接对象
            conn = connectionFactory.createConnection();
            conn.start();
            // 3、 创建session
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 4、创建点对点接收的目标
            Destination destination = session.createQueue("queue1");
            // 5、创建消费者消息
            consumer = session.createConsumer(destination);
            // 6、接收消息
            Message consumerMessage = consumer.receive(1000);
            if (consumerMessage instanceof TextMessage) {
                System.out.println("收到文本消息：" + ((TextMessage) consumerMessage).getText());
            } else {
                System.out.println(consumerMessage);
            }
            session.close();
            conn.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}

package com.zzhy.mytest;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @ClassName Test
 * @Author majt
 * @Date 2018-08-31 10:50
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) throws JMSException {
        String clientId = "127001FirstQueue";

        // 连接到ActiveMQ服务器
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("username","password","tcp://127.0.0.1:61616");
        Connection connection = factory.createConnection();
        //客户端ID,持久订阅需要设置
        connection.setClientID(clientId);
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        // 创建主题
        Topic topic = session.createTopic("FirstQueue");
        // 创建持久订阅,指定客户端ID。
        MessageConsumer consumer = session.createDurableSubscriber(topic,clientId);
        while(true){
            TextMessage tm = (TextMessage) consumer.receive();
            try {
                System.out.println("Received message: " + tm.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.zzhy.mytest;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @ClassName Sender
 * @Author majt
 * @Date 2018-09-07 15:24
 * @Version 1.0
 */
public class Sender {
    public static void main(String[] args) throws JMSException {
        // 连接到ActiveMQ服务器
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        // 创建主题
        Topic topic = session.createTopic("FirstQueue");
        MessageProducer producer = session.createProducer(topic);
        // NON_PERSISTENT 非持久化 PERSISTENT 持久化,发送消息时用使用持久模式
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 0; i <20 ; i++) {
            TextMessage message = session.createTextMessage();
            message.setText(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                            "<ROOT>" +
                            "     " +
                            "    <HEAD>" +
                            "        " +
                            "        <MSGID>DHC"+i+"</MSGID>" +
                            "        " +
                            "        <MSGNAME>XXXXX</MSGNAME>" +
                            "" +
                            "<VERSION>XXX</VERSION>" +
                            "       " +
                            "        <USERCODE>XXX</USERCODE>" +
                            "        " +
                            "        <REQUESTTYPE>XXX</REQUESTTYPE>            " +
                            "        " +
                            "        <DATATYPE>XXX</DATATYPE>            " +
                            "       " +
                            "        <CREATETIME>@创建日期时间</CREATETIME>" +
                            "        <ENCODE>0</ENCODE>" +
                            "    </HEAD>" +
                            "    <BODY>" +
                            "        <ROWS>" +
                            "        <ROW>" +
                            "        </ROW>" +
                            "      </ROWS>  " +
                            "    </BODY>" +
                            "</ROOT>"
            );
//        message.setStringProperty("property", "消息Property");
            // 发布主题消息

            producer.send(message);
            System.out.println("Sent message: " + message.getText());
        }
        session.commit();
        session.close();
        connection.close();
    }
}

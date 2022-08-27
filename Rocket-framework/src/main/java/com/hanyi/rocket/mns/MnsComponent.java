package com.hanyi.rocket.mns;

import com.aliyun.mns.extended.javamessaging.MNSClientWrapper;
import com.aliyun.mns.extended.javamessaging.MNSConnectionFactory;
import com.aliyun.mns.extended.javamessaging.MNSQueueConnection;
import com.aliyun.mns.extended.javamessaging.MNSQueueSession;
import com.aliyun.mns.extended.javamessaging.message.MNSBytesMessage;
import com.aliyun.mns.extended.javamessaging.message.MNSMessageHelper;
import com.aliyun.mns.extended.javamessaging.message.MNSObjectMessage;
import com.aliyun.mns.extended.javamessaging.message.MNSTextMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 阿里云mns消息组件
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-27 14:23
 */
@Component
public class MnsComponent {

    String queueName = "<yourQueueName>";

    private MNSQueueConnection connection;

    @PostConstruct
    public void init() throws JMSException {
        String accessKeyId = "<yourAccessKeyId>";
        String accessKeySecret = "<yourAccessKeySecret>";
        String endpoint = "<yourEndpoint>";
        MNSConnectionFactory factory = MNSConnectionFactory.builder()
                .withAccessKeyId(accessKeyId)
                .withAccessKeySecret(accessKeySecret)
                .withEndpoint(endpoint)
                .build();

        connection = factory.createQueueConnection();
    }

    /**
     * 创建队列
     */
    public void createQueue() {
        MNSClientWrapper mnsClientWrapper = connection.getMNSClientWrapper();
        mnsClientWrapper.createQueue(queueName);
    }

    /**
     * 发送普通消息
     */
    public void sendMessage() {
        try {
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);

            TextMessage textMessage = session.createTextMessage("Hello JMS! ");
            textMessage.setDoubleProperty("TestFloat", 0.127);
            producer.send(textMessage);

            System.out.println(textMessage.getJMSMessageID());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送延迟消息
     */
    public void sendDelayMessage() {
        try {
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);

            TextMessage textMessage = session.createTextMessage("Hello JMS! ");
            textMessage.setDoubleProperty("TestFloat", 0.127);
            //设置DelaySeconds。
            MNSMessageHelper.setDelaySeconds(textMessage, 10);
            producer.send(textMessage);

            System.out.println(textMessage.getJMSMessageID());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自动消费，AUTO_ACKNOWLEDGE模式消费
     */
    public void autoConsumer() {
        try {
            QueueSession session = connection.createQueueSession(false, MNSQueueSession.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);
            MessageListener listener = message -> {
                try {
                    this.printMessage(message);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            };

            consumer.setMessageListener(listener);
            connection.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void manualConsumer() {
        try {
            QueueSession session = connection.createQueueSession(false, MNSQueueSession.MANUAL_ACKNOWLEDGE);

            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);
            MessageListener listener = message -> {
                try {
                    this.printMessage(message);

                    //消费成功后需手动ACK。
                    message.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            };
            consumer.setMessageListener(listener);
            connection.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印信息
     *
     * @param message 消息
     * @throws JMSException jmsexception
     */
    private void printMessage(Message message) throws JMSException {
        if (message instanceof TextMessage) {
            MNSTextMessage textMessage = (MNSTextMessage) message;

            System.out.println(new Date() + " Receive in listener: " + textMessage.getText());
        } else if (message instanceof BytesMessage) {
            MNSBytesMessage bytesMessage = (MNSBytesMessage) message;
            char readChar = bytesMessage.readChar();
            System.out.println("Read Char: " + readChar);
            int readInt = bytesMessage.readInt();
            System.out.println("Read Int: " + readInt);
        } else if (message instanceof ObjectMessage) {
            MNSObjectMessage objectMessage = (MNSObjectMessage) message;
            Serializable object = objectMessage.getObject();
            System.out.println(object);
        }
    }

}

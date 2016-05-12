package com.company;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

public class Main {
    public static String CONSUMER_PRIORITY = "127";
    private static int numberOfSubscribers = 1;
    private static int numberOfProducers = 1;
    private static boolean useQueue;
    String ActiveMq_URL_1 = "localhost";
    String ActiveMq_URL_2 = "localhost";
    String ActiveMq_Timeout = "1000";

    String url = "failover://(tcp://" + ActiveMq_URL_1 + ":61626,tcp://" + ActiveMq_URL_2 + ":61616)?timeout=" + ActiveMq_Timeout;

    public static void main(String[] args) {
        // write your code here
        Main obj = new Main();
        obj.qCreation();
    }

    public void qCreation() {

        try {
            useQueue = true;
            Connection connection = null;
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination;

            for (int i = 1; i <= numberOfSubscribers; i++) {

                System.out.println("HELLO::: Consumer Priority: " + CONSUMER_PRIORITY);

                if (useQueue) {
                    System.out.println("I am creating Q ");
                    destination = session.createQueue("Connector" + i + "?consumer.exclusive=true&consumer.priority=" + CONSUMER_PRIORITY);
                } else {
                    System.out.println("I am creating Topic ");
                    //Topic topic = topicSession.createTopic("Connector" + i);
                    destination = session.createTopic("Connector" + i + "?consumer.exclusive=true&consumer.priority=" + CONSUMER_PRIORITY);
                }
                //TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
                MessageConsumer consumer = session.createConsumer(destination);

                consumer.setMessageListener(new MessageProcessor("Connector" + i));

                System.out.println("Going to Start Queue: " + i);

                connection.start();
            }
        } catch (Exception e) {
            System.out.println("Exception = " + e);
        }
    }
}

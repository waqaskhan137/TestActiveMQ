package com.company;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by Rana on 5/12/2016.
 */
public class MessageProcessor implements MessageListener {
    public MessageProcessor(String s) {

    }

    @Override
    public void onMessage(Message message) {
        System.out.println("message = " + message);
    }
}

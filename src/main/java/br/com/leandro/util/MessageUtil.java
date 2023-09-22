package br.com.leandro.util;

import br.com.leandro.entity.Message;
import jakarta.jms.JMSException;

import java.util.UUID;

public class MessageUtil {

    public static Message createMessage() {
        Message message = new Message();

        message.setId(UUID.randomUUID().toString());
        message.setText("Test message - " + message.getId());

        return message;

    }

}

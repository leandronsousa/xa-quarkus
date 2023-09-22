package br.com.leandro.service;

import br.com.leandro.entity.Message;
import br.com.leandro.util.MessageUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MessageJms {

    @Inject
    private ConnectionFactory factory;

    @Transactional(Transactional.TxType.REQUIRED)
    public List<Message> consume() throws JMSException {

        List<Message> ret = new ArrayList<>();

        JMSConsumer consumer;
        try (JMSContext context = factory.createContext()) {
            consumer = context.createConsumer(context.createQueue("DEV.QUEUE.1"));

            while (true) {
                jakarta.jms.Message m = consumer.receiveNoWait();

                if (m == null)
                    break;

                String text = m.getStringProperty("text");
                String id = m.getStringProperty("id");

                Message message = new Message();
                message.setText(text);
                message.setId(id);

                ret.add(message);
            }
        }

        return ret;

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public String createAndSend() throws JMSException {

        Message message = MessageUtil.createMessage();

        return send(message);

    }

    private Message convert(jakarta.jms.Message m) throws JMSException {
        Message message = new Message();

        TextMessage tm = (TextMessage) m;

        message.setId(m.getStringProperty("id"));
        message.setText(tm.getText());

        return message;
    }

    private jakarta.jms.Message convert(Message message, JMSContext context) throws JMSException {

        TextMessage textMessage = context.createTextMessage(message.getText());
        textMessage.setStringProperty("id", message.getId());

        return textMessage;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public String send(Message message) throws JMSException{

        try (JMSContext context = factory.createContext()) {

            JMSProducer producer = context.createProducer().setDeliveryMode(DeliveryMode.PERSISTENT);

            Queue queue = context.createQueue("DEV.QUEUE.1");

            jakarta.jms.Message m = convert(message, context);

            producer.send(queue, m);

            return m.getStringProperty("id");
        }

    }

}

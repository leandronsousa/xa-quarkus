package br.com.leandro.service;

import br.com.leandro.entity.Message;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MessageJms {

    @Inject
    private ConnectionFactory cf;

    @Transactional(Transactional.TxType.REQUIRED)
    public List<Message> consume() throws JMSException {

        List<Message> ret = new ArrayList<>();

        try (JMSContext context = cf.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            JMSConsumer consumer = context.createConsumer(context.createQueue("DEV.QUEUE.1"));

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

}

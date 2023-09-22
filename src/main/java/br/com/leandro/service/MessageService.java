package br.com.leandro.service;

import br.com.leandro.entity.Message;
import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MessageService {

    @Inject
    private MessageDB messageDB;

    @Inject
    private MessageJms messageJms;

    @Transactional(Transactional.TxType.REQUIRED)
    public void findAllAndSend() {

        List<Message> messages = messageDB.findAll();

        messages.forEach(m -> {
            try {
                messageJms.send(m);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public String findByIdAndSend(String id) throws JMSException {

        try {
            QuarkusTransaction.begin();

            Message message = messageDB.findById(id);

            messageJms.send(message);

            messageDB.delete(message);

//            throwException();

            QuarkusTransaction.commit();

            return message.getId();
        } catch (RuntimeException e) {
            e.printStackTrace();
            QuarkusTransaction.rollback();
            throw e;
        }

    }

    private static void throwException() {
        throw new RuntimeException("Erro de teste");
    }

    public List<Message> consumeAndSave() throws JMSException {

        try {
            QuarkusTransaction.begin();

            List<Message> messages = messageJms.consume();

            throwException();

            List<Message> saved = messageDB.saveAll(messages);

            QuarkusTransaction.commit();

            return saved;

        } catch (Exception e) {
            e.printStackTrace();
            QuarkusTransaction.rollback();
            throw e;
        }

    }
}

package br.com.leandro.service;

import br.com.leandro.entity.Message;
import br.com.leandro.repository.MessageRepository;
import br.com.leandro.util.MessageUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MessageDB {

    @Inject
    private MessageRepository repository;

    @Transactional(Transactional.TxType.REQUIRED)
    public Message save() {

        Message message = MessageUtil.createMessage();

        repository.save(message);

        return message;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Message> findAll() {
        return repository.findAll();
    }

    public Message findById(String id) {
        return repository.findById(id);
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void delete(Message message) {
        repository.deleteById(message);
    }

    public List<Message> saveAll(List<Message> messages) {
        return repository.saveAll(messages);
    }
}

package br.com.leandro.service;

import br.com.leandro.entity.Message;
import br.com.leandro.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MessageService {

    @Inject
    private MessageRepository repository;

    @Transactional(Transactional.TxType.REQUIRED)
    public Message save(Message message) {

        message.setId(UUID.randomUUID().toString());

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

}

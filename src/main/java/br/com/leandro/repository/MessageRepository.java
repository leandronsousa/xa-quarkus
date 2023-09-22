package br.com.leandro.repository;

import br.com.leandro.entity.Message;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MessageRepository {

    @Inject
    private EntityManager em;

    @ConfigProperty(name = "hibernate.jdbc.batch_size")
    private int batchSize;

    public Message save(Message message) {
        em.persist(message);
        return message;
    }

    public List<Message> findAll() {
        return em.createQuery("select m from Message m").getResultList();
    }

    public Message findById(String id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> cq = cb.createQuery(Message.class);
        Root<Message> root = cq.from(Message.class);
        return em.createQuery(cq.select(root).where(cb.equal(root.get("id"), id))).getSingleResult();
    }

    public void deleteById(Message message) {
        em.remove(message);
    }

    public List<Message> saveAll(List<Message> messages) {
        final List<Message> saved = new ArrayList<>(messages.size());

        int i = 0;

        for (Message m: messages) {
            saved.add(save(m));
            i++;

            if (i % batchSize == 0) {
                em.flush();
                em.clear();
            }
        }


        return saved;
    }
}

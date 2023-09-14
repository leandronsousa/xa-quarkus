package br.com.leandro.repository;

import br.com.leandro.entity.Message;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@ApplicationScoped
public class MessageRepository {

    @Inject
    private EntityManager em;

    public void save(Message message) {
        em.persist(message);
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
}

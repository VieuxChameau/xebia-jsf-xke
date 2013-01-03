package fr.xebia.xke.jsfdemo.dao;

import fr.xebia.xke.jsfdemo.entity.Comment;
import fr.xebia.xke.jsfdemo.entity.Slot;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class SlotDao {

    @PersistenceContext(unitName = "xke")
    private EntityManager entityManager;

    public Slot getById(int slotId) {
        return entityManager.createNamedQuery("Slot.getSlotById", Slot.class).setParameter("slotId", slotId).getSingleResult();
    }

    public List<Slot> getAll() {
        return entityManager.createNamedQuery("Slot.getAll", Slot.class).getResultList();
    }

    public void create(Slot slot) {
        entityManager.persist(slot);
    }

    public Slot update(Slot slot) {
        return entityManager.merge(slot);
    }

    public void delete(Slot slot) {
        entityManager.remove(slot);
    }

    public void createComment(final Comment newComment) {
        entityManager.persist(newComment);
    }

    public Integer countCommentsForSlot(final Integer slotId) {
        return entityManager.createNamedQuery("Comment.countCommentsForSlot", Number.class).setParameter("slotId", slotId).getSingleResult().intValue();
    }

    public List<Comment> getAllCommentsForSlot(final Integer slotId) {
        return entityManager.createNamedQuery("Comment.getAllCommentsForSlot", Comment.class).setParameter("slotId", slotId).getResultList();
    }

    public List<Comment> getCommentsForSlot(final Integer slotId, int firstResult, int pageSize) {
        final TypedQuery<Comment> query = entityManager.createNamedQuery("Comment.getAllCommentsForSlot", Comment.class);
        query.setParameter("slotId", slotId);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }
}

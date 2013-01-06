package fr.xebia.xke.jsfdemo.dao;

import fr.xebia.xke.jsfdemo.entity.Comment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CommentDao {

    @PersistenceContext(unitName = "xke")
    private EntityManager entityManager;

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

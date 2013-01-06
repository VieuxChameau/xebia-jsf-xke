package fr.xebia.xke.jsfdemo.dao;

import fr.xebia.xke.jsfdemo.entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDao {

    @PersistenceContext(unitName = "xke")
    private EntityManager entityManager;

    public User getById(Integer slotId) {
        return entityManager.createNamedQuery("User.getUserById", User.class).setParameter("userId", slotId).getSingleResult();
    }

    public User getByEmail(String email) {
        try {
            return entityManager.createNamedQuery("User.getUserByEmail", User.class).setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            // Silent catch return null instead of not found exception
        }
        return null;
    }

    public User createUser(final User newUser) {
        entityManager.persist(newUser);
        return newUser;
    }
}

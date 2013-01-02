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
}

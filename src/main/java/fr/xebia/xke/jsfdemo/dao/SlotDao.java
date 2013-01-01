package fr.xebia.xke.jsfdemo.dao;

import fr.xebia.xke.jsfdemo.entity.Slot;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SlotDao {

    @PersistenceContext(unitName= "xke")
    private EntityManager entityManager;

    public Slot getById(int slotId) {
        return entityManager.createNamedQuery("Slot.getSlotById", Slot.class).setParameter("slotId", slotId).getSingleResult();
    }

    public List<Slot> getAll() {
        return entityManager.createNamedQuery("Slot.listAll", Slot.class).getResultList();
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
}

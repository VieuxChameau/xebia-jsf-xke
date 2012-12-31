package fr.xebia.xke.jsfdemo.service;

import fr.xebia.xke.jsfdemo.domain.Slot;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SlotDao implements SlotService {

    @PersistenceContext(unitName= "XkePU")
    private EntityManager entityManager;

    @Override
    public Slot getSlotById(String slotId) {
        return entityManager.createNamedQuery("Slot.getSlotById", Slot.class).setParameter("slotId", slotId).getSingleResult();
    }

    @Override
    public List<Slot> listSlots() {
        return entityManager.createNamedQuery("Slot.listAll", Slot.class).getResultList();
    }


    @Override
    public void createSlot(Slot slot) {
        entityManager.persist(slot);
    }
}

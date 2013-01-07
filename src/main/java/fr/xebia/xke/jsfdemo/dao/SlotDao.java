package fr.xebia.xke.jsfdemo.dao;

import fr.xebia.xke.jsfdemo.entity.Slot;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import org.joda.time.DateTime;

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

    public List<Slot> getSlotsForNextMonths(int month) {
        final DateTime minDate = new DateTime().dayOfMonth().withMinimumValue();
        final DateTime maxDate = new DateTime().plusMonths(month).dayOfMonth().withMaximumValue();


        return entityManager.createNamedQuery("Slot.getSlotsForNextMonths", Slot.class)
                .setParameter("minDate", minDate.toDate(), TemporalType.DATE)
                .setParameter("maxDate", maxDate.toDate(), TemporalType.DATE)
                .getResultList();
    }

    public void create(Slot slot) {
        entityManager.persist(slot);
    }

    public Slot update(Slot slot) {
        return entityManager.merge(slot);
    }

    public void delete(Slot slot) {
        entityManager.remove(entityManager.merge(slot));
    }
}

package fr.xebia.xke.jsfdemo.controller;

import fr.xebia.xke.jsfdemo.dao.SlotDao;
import fr.xebia.xke.jsfdemo.entity.Slot;
import fr.xebia.xke.jsfdemo.enums.SlotType;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

@ManagedBean
@ViewScoped
public class SlotController implements Serializable {

    public static final Set<SlotType> SLOT_TYPES = EnumSet.allOf(SlotType.class);

    public static final List<String> SLOT_DURATIONS = newArrayList("5 min", "10 min", "15 min", "20 min", "30 min", "45 min",
            "1 h", "1 h 15", "1 h 30", "1 h 45", "2 h", "2 h 30", "3 h",
            "3 h 30", "4 h", "5 h", "6 h", "7 h", "8 h");

    @Inject
    private SlotDao slotDao;

    private String slotId;
    private Slot slot;
    private List<Slot> slots;

    public String create() {
        slotDao.create(slot);
        return "pretty:home";
    }

    public String update() {
        slotDao.update(slot);
        return "pretty:home";
    }

    public String deleteSlot() {
        slotDao.delete(slot);
        return "pretty:home";
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public Slot getSlot() {
        if (slotId != null) {
            slot = slotDao.getById(Integer.parseInt(slotId));
        } else if (slot == null) {
            slot = new Slot();
        }
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public List<Slot> getSlots() {
        if (slots == null) {
            slots = slotDao.getAll();
        }
        return slots;
    }

    public List<String> getSlotDurations() {
        return SLOT_DURATIONS;
    }

    public Set<SlotType> getSlotTypes() {
        return SLOT_TYPES;
    }
}

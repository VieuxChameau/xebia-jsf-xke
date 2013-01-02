package fr.xebia.xke.jsfdemo.controller;

import fr.xebia.xke.jsfdemo.dao.SlotDao;
import fr.xebia.xke.jsfdemo.entity.Slot;
import fr.xebia.xke.jsfdemo.enums.SlotType;
import org.joda.time.YearMonth;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;

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
    private Map<YearMonth, List<Slot>> slotsByYearMonth;
    private Slot randomSlot;

    public String create() {
        slotDao.create(getSlot());
        return "pretty:home";
    }

    public String update() {
        slotDao.update(getSlot());
        return "pretty:home";
    }

    public Slot getRandomSlot() {
        if (randomSlot == null) {
            List<Slot> slots = getSlots();
            if (!slots.isEmpty()) {
                randomSlot = slots.get(new Random().nextInt(slots.size()));
            }
        }
        return randomSlot;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public Slot getSlot() {
        if (slot == null) {
            if (slotId != null) {
                slot = slotDao.getById(Integer.parseInt(slotId));
            } else {
                slot = new Slot();
            }
        }
        return slot;
    }

    public List<Slot> getSlots() {
        if (slots == null) {
            slots = slotDao.getAll();
        }
        return slots;
    }

    public Map<YearMonth, List<Slot>> getSlotsByYearMonth() {
        if (slotsByYearMonth == null) {
            slotsByYearMonth = new TreeMap<>();
            for (Slot slot : getSlots()) {
                YearMonth yearMonth = new YearMonth(slot.getScheduleDate());
                if (slotsByYearMonth.containsKey(yearMonth)) {
                    List<Slot> list = slotsByYearMonth.get(yearMonth);
                    list.add(slot);
                    slotsByYearMonth.put(yearMonth, list);
                } else {
                    slotsByYearMonth.put(yearMonth, newArrayList(slot));
                }
            }
        }
        return slotsByYearMonth;
    }

    public List<String> getSlotDurations() {
        return SLOT_DURATIONS;
    }

    public Set<SlotType> getSlotTypes() {
        return SLOT_TYPES;
    }
}

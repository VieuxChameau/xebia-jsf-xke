package fr.xebia.xke.jsfdemo.web;

import fr.xebia.xke.jsfdemo.domain.Slot;
import fr.xebia.xke.jsfdemo.domain.SlotType;
import fr.xebia.xke.jsfdemo.service.SlotDao;
import fr.xebia.xke.jsfdemo.service.SlotService;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class SlotManager implements Serializable {

    private static final SlotType[] SLOT_TYPES = SlotType.values();

    // TODO à passer en inject
    //@Inject
    private SlotService slotService = new SlotDao();

    private String slotId;

    private Slot slot = new Slot();

    private List<Slot> slots;

    public void initList() {
        
        slots = slotService.listSlots();
    }

    public String initView() {

        slot = slotService.getSlotById(slotId);
        return null;
    }

    public String createSlot() {
        
        slotService.createSlot(slot);
        slot = new Slot();
        return null;
    }

    public String editSlot() {
        return null;
    }

    public String deleteSlot() {
        return null;
    }

    public SlotType[] getSlotTypes() {
        return SLOT_TYPES;
    }

    /**
     * @return the slotId
     */
    public String getSlotId() {
        return slotId;
    }

    /**
     * @param slotId the slotId to set
     */
    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    /**
     * @return the slot
     */
    public Slot getSlot() {
        return slot;
    }

    /**
     * @param slot the slot to set
     */
    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    /**
     * @return the slots
     */
    public List<Slot> getSlots() {
        return slots;
    }
}

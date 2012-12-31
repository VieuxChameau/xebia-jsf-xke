package fr.xebia.xke.jsfdemo.service;

import fr.xebia.xke.jsfdemo.domain.Slot;
import java.util.List;

public interface SlotService {

    void createSlot(Slot slot);

    Slot getSlotById(String slotId);

    List<Slot> listSlots();
}

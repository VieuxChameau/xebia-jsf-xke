package fr.xebia.xke.jsfdemo.controller;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newTreeMap;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import fr.xebia.xke.jsfdemo.dao.SlotDao;
import fr.xebia.xke.jsfdemo.entity.Slot;
import fr.xebia.xke.jsfdemo.entity.User;
import fr.xebia.xke.jsfdemo.enums.SlotType;
import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.joda.time.YearMonth;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@URLMappings(mappings = {
    @URLMapping(id = "home", pattern = "/slots", viewId = "/home.xhtml"),
    @URLMapping(id = "createSlot", pattern = "/slots/new", viewId = "/slot/form.xhtml"),
    @URLMapping(id = "viewSlot", pattern = "/slots/view/#{slotId : slotController.slotId}", viewId = "/slot/view.xhtml"),
    @URLMapping(id = "editSlot", pattern = "/slots/edit/#{slotId : slotController.slotId}", viewId = "/slot/form.xhtml")})
@ManagedBean
@ViewScoped
public class SlotController extends AbstractController implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(SlotController.class);

    public static final Set<SlotType> SLOT_TYPES = EnumSet.allOf(SlotType.class);

    public static final List<String> SLOT_DURATIONS = newArrayList("5 min", "10 min", "15 min", "20 min", "30 min", "45 min",
            "1 h", "1 h 15", "1 h 30", "1 h 45", "2 h", "2 h 30", "3 h",
            "3 h 30", "4 h", "5 h", "6 h", "7 h", "8 h");

    @Inject
    private SlotDao slotDao;

    @ManagedProperty(value = "#{userSession.connectedUser}")
    private User currentUser;

    @ManagedProperty(value = "#{commentController}")
    private CommentController commentController;

    private String slotId;

    private Slot slot;

    private boolean editAllowed;

    private Map<YearMonth, List<Slot>> slotsByYearMonth;

    /* ** ** ** ** ** * Url Mapping Actions * ** ** ** ** ** */
    @URLAction(mappingId = "createSlot", onPostback = false)
    public String initCreateSlot() {
        if (!isAuthenticated()) {
            return returnToLoginNotAuthenticated();
        }

        slot = new Slot();
        slot.setScheduleDate(new Date());
        return null;
    }

    @URLAction(mappingId = "viewSlot", onPostback = false)
    public String initViewSlot() {
        if (!isAuthenticated()) {
            return returnToLoginNotAuthenticated();
        }

        // On charge le slot demande
        try {
            final Integer wantedSlotId = Integer.parseInt(slotId);
            slot = slotDao.getById(wantedSlotId);
            editAllowed = userCanEditSlot();
            commentController.initComments(wantedSlotId);
            return null;
        } catch (Exception ex) { // Slot non trouve
            logger.warn("Failed to load the slot {} - Reason : {}", slotId, ex);
            Messages.create("Unknown slot {0}", slotId).error().add();
        }
        return "pretty:home";
    }

    @URLAction(mappingId = "editSlot", onPostback = false)
    public String initEditSlot() {
        if (!isAuthenticated()) {
            return returnToLoginNotAuthenticated();
        }

        try {
            final Integer wantedSlotId = Integer.parseInt(slotId);
            slot = slotDao.getById(wantedSlotId);
            if (userCanEditSlot()) {
                return null;
            }
            Messages.create("You are not allowed to edit this slot").error().add();
        } catch (Exception ex) { // Slot non trouve
            logger.warn("Failed to load the slot {} - Reason : {}", slotId, ex);
            Messages.create("Unknown slot {0}", slotId).error().add();
        }
        return "pretty:home";
    }

    private boolean userCanEditSlot() {
        return currentUser != null && (currentUser.equals(slot.getAuthor()) || currentUser.isAdministrator());
    }

    @URLAction(mappingId = "home", onPostback = false)
    public String initViewSlots() {
        if (!isAuthenticated()) {
            return returnToLoginNotAuthenticated();
        }

        final List<Slot> slots = slotDao.getAll();

        slotsByYearMonth = newTreeMap();
        for (Slot aSlot : slots) {
            YearMonth yearMonth = new YearMonth(aSlot.getScheduleDate());
            if (slotsByYearMonth.containsKey(yearMonth)) {
                List<Slot> list = slotsByYearMonth.get(yearMonth);
                list.add(aSlot);
                slotsByYearMonth.put(yearMonth, list);
            } else {
                slotsByYearMonth.put(yearMonth, newArrayList(aSlot));
            }
        }

        // random slot
        if (!slots.isEmpty()) {
            slot = slots.get(new Random().nextInt(slots.size()));
        }
        return null;
    }

    /* ** ** ** ** ** * Actions Method * ** ** ** ** ** */
    public String create() {
        try {
            slot.setAuthor(currentUser);
            slotDao.create(slot);
            Messages.addInfo(null, "Creation of slot succeed - Id {0}", slot.getId());
            logger.debug("Creation of slot {} succeed", slot.getId());
            slotId = slot.getId().toString();
            return "pretty:viewSlot";
        } catch (Exception ex) {
            logger.error("Failed to create slot - Reason :", ex);
            Messages.addError(null, "Slot Creation failed !");
        }
        return null; // Erreur on reste sur la page
    }

    public String update() {
        slotDao.update(slot);
        return "pretty:home";
    }

    public String remove() {
        slotDao.delete(slot);
        return "pretty:home";
    }

    /* ** ** ** ** ** * Getter/Setter * ** ** ** ** ** */
    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public Slot getSlot() {
        return slot;
    }

    public Map<YearMonth, List<Slot>> getSlotsByYearMonth() {
        return slotsByYearMonth;
    }

    public List<String> getSlotDurations() {
        return SLOT_DURATIONS;
    }

    public Set<SlotType> getSlotTypes() {
        return SLOT_TYPES;
    }

    public boolean isEditAllowed() {
        return editAllowed;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCommentController(CommentController commentController) {
        this.commentController = commentController;
    }
}

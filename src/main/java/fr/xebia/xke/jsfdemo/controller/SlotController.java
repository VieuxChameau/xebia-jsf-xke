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
    @URLMapping(id = "home", pattern = "/slots", viewId = "/home.xhtml")})
@ManagedBean
@ViewScoped
public class SlotController extends AbstractController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlotController.class);

    public static final Set<SlotType> SLOT_TYPES = EnumSet.allOf(SlotType.class);

    public static final List<String> SLOT_DURATIONS = newArrayList("5 min", "10 min", "15 min", "20 min", "30 min", "45 min",
            "1 h", "1 h 15", "1 h 30", "1 h 45", "2 h", "2 h 30", "3 h",
            "3 h 30", "4 h", "5 h", "6 h", "7 h", "8 h");

    private Slot slot;

    private Map<YearMonth, List<Slot>> slotsByYearMonth;

    /* ** ** ** ** ** * Url Mapping Actions * ** ** ** ** ** */
    public String initViewSlots() {
        if (!isAuthenticated()) {
            return returnToLoginNotAuthenticated();
        }

        return null;
    }

    /* ** ** ** ** ** * Actions Method * ** ** ** ** ** */

    /* ** ** ** ** ** * Getter/Setter * ** ** ** ** ** */
}

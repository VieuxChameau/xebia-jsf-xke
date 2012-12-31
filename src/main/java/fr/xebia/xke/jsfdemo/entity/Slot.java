package fr.xebia.xke.jsfdemo.entity;

import fr.xebia.xke.jsfdemo.enums.SlotType;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
    @NamedQuery(name = "Slot.listAll", query = "SELECT s from Slot s"),
    @NamedQuery(name = "Slot.getSlotById", query = "SELECT s FROM Slot s WHERE s.id = :slotId")})
@Entity
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private boolean videoAccepted;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleDate;

    private String duration;
    //private User author;
    //private Set<User> speakers;

    private SlotType slotType;
    //private String file;
    //private List<Comment> comments;
    //private List<Rating> rates;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the videoAccepted
     */
    public boolean isVideoAccepted() {
        return videoAccepted;
    }

    /**
     * @param videoAccepted the videoAccepted to set
     */
    public void setVideoAccepted(boolean videoAccepted) {
        this.videoAccepted = videoAccepted;
    }

    /**
     * @return the scheduleDate
     */
    public Date getScheduleDate() {
        return scheduleDate;
    }

    /**
     * @param scheduleDate the scheduleDate to set
     */
    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return the slotType
     */
    public SlotType getSlotType() {
        return slotType;
    }

    /**
     * @param slotType the slotType to set
     */
    public void setSlotType(SlotType slotType) {
        this.slotType = slotType;
    }
}

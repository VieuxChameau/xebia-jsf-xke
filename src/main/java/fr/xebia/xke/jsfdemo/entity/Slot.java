package fr.xebia.xke.jsfdemo.entity;

import fr.xebia.xke.jsfdemo.enums.SlotType;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "Slot.getAll", query = "SELECT s from Slot s WHERE month(s.scheduleDate) >= month(current_date()) and year(s.scheduleDate) >= year(current_date())"),
        @NamedQuery(name = "Slot.getSlotById", query = "SELECT s FROM Slot s WHERE s.id = :slotId")})
@Entity
public class Slot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 20, max = 60)
    private String title;

    @NotBlank
    @Lob
    private String description;

    private boolean videoAccepted;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleDate;

    private String duration;

    @OneToOne
    private User author;

    @OneToMany
    private Set<User> speakers;

    @Enumerated
    private SlotType slotType;
    //private String file;
    //private List<Comment> comments;
    //private List<Rating> rates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionAbbreviated() {
        return StringUtils.abbreviate(description, 240);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVideoAccepted() {
        return videoAccepted;
    }

    public void setVideoAccepted(boolean videoAccepted) {
        this.videoAccepted = videoAccepted;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public void setSlotType(SlotType slotType) {
        this.slotType = slotType;
    }


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<User> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(Set<User> speakers) {
        this.speakers = speakers;
    }
}

package fr.xebia.xke.jsfdemo.entity;

import com.google.common.base.Objects;
import fr.xebia.xke.jsfdemo.enums.SlotType;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.validation.constraints.NotNull;

@NamedQueries({
    @NamedQuery(name = "Slot.getAll", query = "SELECT s from Slot s"),
    @NamedQuery(name = "Slot.getSlotById", query = "SELECT s FROM Slot s WHERE s.id = :slotId"),
    @NamedQuery(name = "Slot.getSlotsForNextMonths", query = "SELECT s FROM Slot s WHERE s.scheduleDate BETWEEN :minDate AND :maxDate ORDER BY s.scheduleDate ASC")})
@Entity
public class Slot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 10, max = 60)
    private String title;

    @NotBlank
    @Lob
    private String description;

    private boolean videoAccepted;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleDate;

    @NotNull
    private String duration;

    @OneToOne
    private User author;

    @OneToMany
    private Set<User> speakers;

    @NotNull
    @Enumerated
    private SlotType slotType;

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

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Slot)) {
            return false;
        }
        Slot other = (Slot) object;
        return Objects.equal(id, other.id);
    }
}

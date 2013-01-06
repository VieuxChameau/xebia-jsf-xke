package fr.xebia.xke.jsfdemo.entity;

import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

@NamedQueries({
    @NamedQuery(name = "Comment.countCommentsForSlot", query = "SELECT COUNT(c) FROM Comment c WHERE c.slotId = :slotId"),
    @NamedQuery(name = "Comment.getAllCommentsForSlot", query = "SELECT c FROM Comment c INNER JOIN FETCH c.user WHERE c.slotId = :slotId ORDER BY c.postDate DESC")})
@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer slotId;

    @NotBlank
    @Lob
    private String comment;

    @ManyToOne
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        return Objects.equal(id, other.id);
    }
}

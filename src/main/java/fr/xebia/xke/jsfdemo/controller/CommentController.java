package fr.xebia.xke.jsfdemo.controller;

import fr.xebia.xke.jsfdemo.dao.CommentDao;
import fr.xebia.xke.jsfdemo.entity.Comment;
import fr.xebia.xke.jsfdemo.entity.User;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class CommentController implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Inject
    private CommentDao commentDao;

    @ManagedProperty(value = "#{userSession.connectedUser}")
    private User currentUser;

    private Integer slotId;

    private int sumComments;

    private Comment newComment;

    private CommentDataModel dataModel;

    /**
     * Load comments
     */
    public void initComments(final Integer visitedSlotId) {
        slotId = visitedSlotId;
        newComment = new Comment();
        sumComments = commentDao.countCommentsForSlot(slotId);
        dataModel = new CommentDataModel(commentDao, slotId);
        dataModel.setRowCount(sumComments);
    }

    public String postComment() {
        newComment.setSlotId(slotId);
        newComment.setPostDate(new Date());
        newComment.setUser(currentUser);

        try {
            commentDao.createComment(newComment);
            Messages.addInfo(null, "Comment post succeed");
            logger.debug("Creation of comment {} succeed", slotId);
            return "pretty:viewSlot";
        } catch (Exception ex) {
            logger.error("Failed to create comment - Reason :", ex);
            Messages.addError(null, "Comment post fail");
        }
        return null;  // Erreur on reste sur la page
    }

    public Comment getNewComment() {
        return newComment;
    }

    public int getSumComments() {
        return sumComments;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public CommentDataModel getDataModel() {
        return dataModel;
    }
}

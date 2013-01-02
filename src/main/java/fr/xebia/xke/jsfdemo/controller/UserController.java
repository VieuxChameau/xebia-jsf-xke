package fr.xebia.xke.jsfdemo.controller;

import fr.xebia.xke.jsfdemo.dao.UserDao;
import fr.xebia.xke.jsfdemo.entity.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private String userId;

    private User loadedUser;

    @Inject
    private UserDao userDao;

    public String initViewUser() {
        try {
            loadedUser = userDao.getById(Integer.parseInt(userId));
        } catch (Exception e) {
            logger.warn("Failed to load User {} {}", userId, e);
            Messages.addError("Unknow user {0}", userId);
            return "pretty:home";
        }
        return null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getLoadedUser() {
        return loadedUser;
    }

    public void setLoadedUser(User loadedUser) {
        this.loadedUser = loadedUser;
    }
}

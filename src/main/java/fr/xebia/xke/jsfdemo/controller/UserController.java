package fr.xebia.xke.jsfdemo.controller;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import fr.xebia.xke.jsfdemo.dao.UserDao;
import fr.xebia.xke.jsfdemo.entity.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@URLMappings(mappings = {
    @URLMapping(id = "viewUser", pattern = "/users/#{userId : userController.userId}", viewId = "/user/view.xhtml")})
@ManagedBean
@ViewScoped
public class UserController implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private String userId;

    private User loadedUser;

    @Inject
    private UserDao userDao;

    @URLAction(mappingId = "viewUser", onPostback = false)
    public String initViewUser() {
        try {
            loadedUser = userDao.getById(Integer.parseInt(userId));
            return null; // on reste sur la page si on a reussi a charger le user
        } catch (Exception e) {
            logger.warn("Failed to load User {} - Reason : {}", userId, e);
            Messages.create("Unknow user {0}", userId).error().add();
        }
        return "pretty:home";
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

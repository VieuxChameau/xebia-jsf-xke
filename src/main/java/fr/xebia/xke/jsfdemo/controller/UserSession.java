package fr.xebia.xke.jsfdemo.controller;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import fr.xebia.xke.jsfdemo.dao.UserDao;
import fr.xebia.xke.jsfdemo.entity.User;
import fr.xebia.xke.jsfdemo.openid.OpenidConnector;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.util.Messages;

@URLMappings(mappings = {
    @URLMapping(id = "login", pattern = "/login", viewId = "/index.xhtml")})
@ManagedBean
@SessionScoped
public class UserSession extends AbstractController implements Serializable {

    @Inject
    private UserDao userDao;

    private final OpenidConnector openidConnector = new OpenidConnector();

    private User connectedUser;

    private String userSuppliedId;

    private String redirectPage = "index.xhtml";

    public String login() throws IOException {
        final String url = openidConnector.buildAuthRequestUrl(returnToUrl("/openid.xhtml"));
        userSuppliedId = null;
        if (url != null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        }
        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        connectedUser = null;
        return PRETTY_LOGIN;
    }

    /**
     * Create the current url and add another url path fragment on it. Obtain from the current context the url and add another url path fragment at the end
     *
     * @param urlExtension f.e. /nextside.xhtml
     * @return the hole url including the new fragment
     */
    private String returnToUrl(String urlExtension) {
        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return String.format("http://%s:%d%s%s", request.getServerName(), request.getServerPort(), request.getContextPath(), urlExtension);
    }

    public void verifyOpenidResponse() throws IOException {
        System.out.println("verifyOpenidResponse");
        final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        final HttpServletRequest request = (HttpServletRequest) context.getRequest();
        final User openIdUser = openidConnector.verifyResponse(request);
        if (openIdUser != null) {
            connectedUser = userDao.getByEmail(openIdUser.getEmail());
            if (connectedUser == null) { // User pas en base on le sauvegarde
                connectedUser = userDao.createUser(openIdUser);
            }
            redirectPage = "slots";
        } else {
            Messages.create("Failed to authenticate").error().add();
        }
    }

    public String getUserSuppliedId() {
        return userSuppliedId;
    }

    public void setUserSuppliedId(String userSuppliedId) {
        this.userSuppliedId = userSuppliedId;
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public boolean isLoggedIn() {
        return connectedUser != null;
    }

    public String getRedirectPage() {
        return redirectPage;
    }
}

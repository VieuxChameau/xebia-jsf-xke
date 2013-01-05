package fr.xebia.xke.jsfdemo.controller;

import fr.xebia.xke.jsfdemo.entity.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "openid")
@SessionScoped
public class OpenId implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenId.class);

    private User connectedUser;

    private String userSuppliedId;

    private ConsumerManager manager;

    private DiscoveryInformation discovered;

    public String login() throws IOException {
        manager = new ConsumerManager();

        final String url = authRequest(returnToUrl("/openid.xhtml"));
        if (url != null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        }
        return null;
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
        String returnToUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                + context.getApplication().getViewHandler().getActionURL(context, urlExtension);
        return returnToUrl;
    }

    /**
     * Create an authentication request. It performs a discovery on the user-supplied identifier. Attempt it to associate with the OpenID provider and retrieve one dao endpoint for authentication. It
     * adds some attributes for exchange on the AuthRequest. A List of all possible attributes can be found on
     *
     * @see http://www.axschema.org/types/
     *
     * @param returnToUrl
     * @return the URL where the message should be sent
     * @throws IOException
     */
    private String authRequest(String returnToUrl) throws IOException {
        try {
            final List discoveries = manager.discover("https://www.google.com/accounts/o8/id");
            discovered = manager.associate(discoveries);
            final AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

            final FetchRequest fetch = FetchRequest.createFetchRequest();
            fetch.addAttribute("email", "http://axschema.org/contact/email", true);
            fetch.addAttribute("firstName", "http://axschema.org/namePerson/first", true);
            fetch.addAttribute("lastName", "http://axschema.org/namePerson/last", true);

            authReq.addExtension(fetch);
            return authReq.getDestinationUrl(true);
        } catch (OpenIDException e) {
            LOGGER.error("OpenId authRequest fail", e);
        }
        return null;
    }

    public String verifyOpenidResponse(ComponentSystemEvent event) {
        final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        final HttpServletRequest request = (HttpServletRequest) context.getRequest();
        verifyResponse(request);
        return "pretty:home";
    }

    /**
     * Set the class members with date from the authentication response. Extract the parameters from the authentication response (which comes in as a HTTP request from the OpenID provider). Verify the
     * response, examine the verification result and extract the verified identifier.
     *
     * @param httpReq httpRequest
     * @return users identifier.
     */
    private String verifyResponse(final HttpServletRequest httpReq) {
        try {
            final ParameterList response = new ParameterList(httpReq.getParameterMap());

            final StringBuffer receivingURL = httpReq.getRequestURL();
            final String queryString = httpReq.getQueryString();
            if (queryString != null && queryString.length() > 0) {
                receivingURL.append("?").append(httpReq.getQueryString());
            }

            final VerificationResult verification = manager.verify(receivingURL.toString(), response, discovered);

            final Identifier verified = verification.getVerifiedId();
            if (verified != null) {
                final AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();

                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
                    final FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);
                    connectedUser = buildUserFromResponse(fetchResp);
                }
                return verified.getIdentifier();
            } else {
                LOGGER.error("Failed to get informations");
            }
        } catch (OpenIDException e) {
            LOGGER.error("Error during authentication", e);
        }
        return null;
    }

    private User buildUserFromResponse(final FetchResponse openidResponse) {
        final User user = new User();

        final List emails = openidResponse.getAttributeValues("email");
        user.setEmail((String) emails.get(0));
        user.setFirstName(openidResponse.getAttributeValue("firstName"));
        user.setLastName(openidResponse.getAttributeValue("lastName"));

        LOGGER.debug("User created {}", user);

        return user;
    }

    public String getUserSuppliedId() {
        return userSuppliedId;
    }

    public void setUserSuppliedId(String userSuppliedId) {
        this.userSuppliedId = userSuppliedId;
    }

    /**
     * @return the connectedUser
     */
    public User getConnectedUser() {
        return connectedUser;
    }
}

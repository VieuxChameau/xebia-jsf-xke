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
import org.openid4java.consumer.ConsumerException;
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

    private String userSuppliedId; //Users OpenID URL
    private String validatedId;
    private String openIdEmail;
    private String openIdFullName;
    private String openIdFirstName;
    private String openIdLastName;
    private String openIdCountry;
    private ConsumerManager manager;
    private DiscoveryInformation discovered;


    // temporaire le temps de faire fonctionner le SSO
    @PostConstruct
    public void initDefaultUser() {
        connectedUser = new User();
        connectedUser.setId(1);
        connectedUser.setEmail("jdoe@xebia.fr");
        connectedUser.setFirstName("John");
        connectedUser.setLastName("DOE");
        connectedUser.setAdministrator(true);
    }

    public String login() throws IOException {

        try {
            manager = new ForceOpenIdConsumerManager();
        } catch (ConsumerException e) {
            LOGGER.error("Cannot create the Consumer manager {}", e);
        }

        setValidatedId(null);
        String returnToUrl = returnToUrl("/openid.xhtml");
        LOGGER.debug("return url {}", returnToUrl);
        String url = authRequest(returnToUrl);

        LOGGER.debug("Authentication url {}", url);
        if (url != null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        }
        return null;
    }

    /**
     * Create the current url and add another url path fragment on it.
     * Obtain from the current context the url and add another url path fragment at
     * the end
     *
     * @param urlExtension f.e. /nextside.xhtml
     * @return the hole url including the new fragment
     */
    private String returnToUrl(String urlExtension) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String returnToUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                + context.getApplication().getViewHandler().getActionURL(context, urlExtension);
        return returnToUrl;
    }

    /**
     * Create an authentication request.
     * It performs a discovery on the user-supplied identifier. Attempt it to
     * associate with the OpenID provider and retrieve one dao endpoint
     * for authentication. It adds some attributes for exchange on the AuthRequest.
     * A List of all possible attributes can be found on @see http://www.axschema.org/types/
     *
     * @param returnToUrl
     * @return the URL where the message should be sent
     * @throws IOException
     */
    private String authRequest(String returnToUrl) throws IOException {
        try {
            List discoveries = manager.discover(getUserSuppliedId());
            LOGGER.debug("UserSuppliedId {} - discoveries {}", getUserSuppliedId(), discoveries);
            discovered = manager.associate(discoveries);
            AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

            FetchRequest fetch = FetchRequest.createFetchRequest();

            fetch.addAttribute("email", "http://axschema.org/contact/email", true);
            //fetch.addAttribute("email", "http://schema.openid.net/contact/email", true);
            fetch.addAttribute("FirstName", "http://schema.openid.net/namePerson/first", true);
            fetch.addAttribute("LastName", "http://schema.openid.net/namePerson/last", true);
            //fetch.addAttribute("fullname", "http://schema.openid.net/namePerson", true);
            /* Some other attributes ... */

            authReq.addExtension(fetch);
            return authReq.getDestinationUrl(true);
        } catch (OpenIDException e) {
            // TODO
        }
        return null;
    }

    public void verify() {
        ExternalContext context = javax.faces.context.FacesContext
                .getCurrentInstance().getExternalContext();

        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        setValidatedId(verifyResponse(request));
    }

    /**
     * Set the class members with date from the authentication response.
     * Extract the parameters from the authentication response (which comes
     * in as a HTTP request from the OpenID provider). Verify the response,
     * examine the verification result and extract the verified identifier.
     *
     * @param httpReq httpRequest
     * @return users identifier.
     */
    private String verifyResponse(HttpServletRequest httpReq) {
        try {
            ParameterList response =
                    new ParameterList(httpReq.getParameterMap());

            StringBuffer receivingURL = httpReq.getRequestURL();
            String queryString = httpReq.getQueryString();
            if (queryString != null && queryString.length() > 0) {
                receivingURL.append("?").append(httpReq.getQueryString());
            }

            VerificationResult verification = manager.verify(
                    receivingURL.toString(),
                    response, discovered);

            Identifier verified = verification.getVerifiedId();
            LOGGER.debug("Identifier {}", verified);
            if (verified != null) {
                AuthSuccess authSuccess =
                        (AuthSuccess) verification.getAuthResponse();

                LOGGER.debug("AuthSuccess {}", authSuccess);
                LOGGER.debug("Claimed {}", authSuccess.getClaimed());
                LOGGER.debug("Extensions {}", authSuccess.getExtensions());
                LOGGER.debug("Identity {}", authSuccess.getIdentity());
                LOGGER.debug("Mode {}", authSuccess.getMode());
                LOGGER.debug("Signlist {}", authSuccess.getSignList());
                LOGGER.debug("Required Field {}", authSuccess.getRequiredFields());

                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
                    LOGGER.debug("Authentication suceed");
                    FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);

                    List emails = fetchResp.getAttributeValues("email");

                    setOpenIdEmail((String) emails.get(0));

                }
                return verified.getIdentifier();
            }
        } catch (OpenIDException e) {
            // TODO
        }
        return null;
    }

    /**
     * hidden member for onLoad/Init event.
     *
     * @return always return the string pageLoaded
     */
    public String onLoadMethod(ComponentSystemEvent event) {
        LOGGER.debug("Onload method {}", event);
        verify();
        LOGGER.debug("Validated id {}", validatedId);
        return "pretty:home";
    }

    public String getUserSuppliedId() {
        return userSuppliedId;
    }

    public void setUserSuppliedId(String userSuppliedId) {
        this.userSuppliedId = userSuppliedId;
    }

    public String getValidatedId() {
        return validatedId;
    }

    public void setValidatedId(String validatedId) {
        this.validatedId = validatedId;
    }

    public String getOpenIdEmail() {
        return openIdEmail;
    }

    public void setOpenIdEmail(String openIdEmail) {
        this.openIdEmail = openIdEmail;
    }

    public String getOpenIdFullName() {
        return openIdFullName;
    }

    public void setOpenIdFullName(String openIdFullName) {
        this.openIdFullName = openIdFullName;
    }

    public String getOpenIdFirstName() {
        return openIdFirstName;
    }

    public void setOpenIdFirstName(String openIdFirstName) {
        this.openIdFirstName = openIdFirstName;
    }

    public String getOpenIdLastName() {
        return openIdLastName;
    }

    public void setOpenIdLastName(String openIdLastName) {
        this.openIdLastName = openIdLastName;
    }

    public String getOpenIdCountry() {
        return openIdCountry;
    }

    public void setOpenIdCountry(String openIdCountry) {
        this.openIdCountry = openIdCountry;
    }

    /**
     * @return the connectedUser
     */
    public User getConnectedUser() {
        return connectedUser;
    }


}

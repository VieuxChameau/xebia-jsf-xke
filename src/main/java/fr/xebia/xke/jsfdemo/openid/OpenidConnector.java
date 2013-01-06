package fr.xebia.xke.jsfdemo.openid;

import fr.xebia.xke.jsfdemo.entity.User;
import java.io.IOException;
import java.util.List;
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

/**
 * OpenId doc https://developers.google.com/accounts/docs/OpenID?hl=fr
 */
public class OpenidConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenidConnector.class);

    private ConsumerManager manager;

    private DiscoveryInformation discovered;

    public OpenidConnector() {
        manager = new ConsumerManager();
    }

    /**
     * Create an authentication request. It performs a discovery on the user-supplied identifier. Attempt it to associate with the OpenID provider and retrieve one dao endpoint for authentication. It
     * adds some attributes for exchange on the AuthRequest.
     */
    public String buildAuthRequestUrl(final String returnToUrl) throws IOException {
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

    /**
     * Extract the parameters from the authentication response (which comes in as a HTTP request from the OpenID provider). Verify the
     * response, examine the verification result and extract the user.
     */
    public User verifyResponse(final HttpServletRequest httpReq) {
        try {
            final ParameterList response = new ParameterList(httpReq.getParameterMap());

            final StringBuffer receivingURL = httpReq.getRequestURL();
            System.out.println("receivingURL " + receivingURL);
            final String queryString = httpReq.getQueryString();
            System.out.println("queryString " + queryString);
            if (queryString != null && queryString.length() > 0) {
                receivingURL.append("?").append(httpReq.getQueryString());
            }

            final VerificationResult verification = manager.verify(receivingURL.toString(), response, discovered);

            final Identifier verified = verification.getVerifiedId();
            if (verified != null) {
                final AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();

                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
                    final FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);
                    return buildUserFromResponse(fetchResp);
                }
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
}

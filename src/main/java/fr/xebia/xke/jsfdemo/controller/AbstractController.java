package fr.xebia.xke.jsfdemo.controller;

import org.omnifaces.util.Faces;

public abstract class AbstractController {

    protected static final String PRETTY_LOGIN = "pretty:login";

    public final boolean isAuthenticated() {
        final UserSession userSession = Faces.getSessionAttribute("userSession");
        return userSession != null && userSession.isLoggedIn();
    }

    protected final String returnToLoginNotAuthenticated() {
        return PRETTY_LOGIN;
    }
}

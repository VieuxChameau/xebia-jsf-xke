package fr.xebia.xke.jsfdemo.controller;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

public abstract class AbstractController {

    protected static final String PRETTY_LOGIN = "pretty:login";

    public final boolean isAuthenticated() {
        final UserSession userSession = Faces.getSessionAttribute("userSession");
        return userSession != null && userSession.isLoggedIn();
    }

    protected final String returnToLoginNotAuthenticated() {
        Messages.create("This page required to be authenticated").error().add();
        return PRETTY_LOGIN;
    }
}

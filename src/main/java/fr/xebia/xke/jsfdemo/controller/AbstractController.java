package fr.xebia.xke.jsfdemo.controller;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import org.omnifaces.util.Faces;


public abstract class AbstractController {

    public boolean isLoggedIn() {
        final OpenId openId = Faces.getSessionAttribute("openid");
        return openId != null && openId.isLoggedIn();
    }
}

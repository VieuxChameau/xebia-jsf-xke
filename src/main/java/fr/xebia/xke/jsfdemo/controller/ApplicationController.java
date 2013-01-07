package fr.xebia.xke.jsfdemo.controller;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

@ApplicationScoped
@ManagedBean
public class ApplicationController {

    @PostConstruct
    public void initializeApp() {
        /* TODO
         * Messages.setResolver(new Messages.Resolver() {
            private static final String BASE_NAME = "fr.xebia.xke.jsfdemo.controllers_messages_en";

            @Override
            public String getMessage(String message, Object... params) {
                final ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, Faces.getLocale());
                if (bundle.containsKey(message)) {
                    message = bundle.getString(message);
                }
                return MessageFormat.format(message, params);
            }
        });*/
    }
}

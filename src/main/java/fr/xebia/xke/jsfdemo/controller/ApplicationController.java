package fr.xebia.xke.jsfdemo.controller;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

/*
 * If eager=”true” and scope is application, then this bean must be created when the application starts and not during the first reference to the bean
 */
@ApplicationScoped
@ManagedBean(eager = true)
public class ApplicationController {

    @PostConstruct
    public void initializeApp() {
        Messages.setResolver(new Messages.Resolver() {
            private static final String BASE_NAME = "controllers_messages";

            @Override
            public String getMessage(String message, Object... params) {
                final ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, Faces.getLocale());
                if (bundle.containsKey(message)) {
                    message = bundle.getString(message);
                }
                return MessageFormat.format(message, params);
            }
        });
    }
}

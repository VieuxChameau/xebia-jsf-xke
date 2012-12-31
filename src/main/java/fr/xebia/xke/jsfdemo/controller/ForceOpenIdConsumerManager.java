package fr.xebia.xke.jsfdemo.controller;


import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryException;

import java.util.List;

public class ForceOpenIdConsumerManager extends ConsumerManager {
    public ForceOpenIdConsumerManager() throws ConsumerException {
        super();
    }

    @Override
    public List discover(String identifier) throws DiscoveryException {
        String xebiaIdentifier="https://sso.xebia.com/openidserver/users/"+identifier;
        return super.discover(xebiaIdentifier);
    }
}

package com.feedglobo;

import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.feedglobo.FeedResource;
import com.feedglobo.converters.XMLConvert;

@ApplicationPath("/*")
public class AppConfig extends ResourceConfig {
    public AppConfig() {
        register(FeedResource.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(XMLConvert.class);
            }
        });
    }
}
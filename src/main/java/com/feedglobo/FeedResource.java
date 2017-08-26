package com.feedglobo;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.feedglobo.interfaces.IXMLConvert;
import com.feedglobo.jwt.Autorizado;


/**
 * Root resource (exposed at "feed" path)
 */

@Autorizado
@Path("feed")
public class FeedResource {

	@Inject   
	public FeedResource(IXMLConvert converte) {
		this.converte = converte;
	}
	
	@DefaultValue("http://revistaautoesporte.globo.com/rss/ultimas/feed.xml") @QueryParam("xmlurl")
	private String url;
	
	private final IXMLConvert converte;
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "APPLICATION_JSON" media type.
     *
     * @return String that will be returned as a APPLICATION_JSON response.
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws MalformedURLException 
     */ 
    @GET
    @Inject 
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getIt(@Context SecurityContext sc) throws Exception {
    	
    	if (sc.isUserInRole("PreferredCustomer")) {
            System.out.println("true");
        } else {
        	System.out.println("false");
        }
        
        return converte.converteXMLtoJson(url);
    }
}

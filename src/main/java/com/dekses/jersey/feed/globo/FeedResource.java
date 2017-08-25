package com.dekses.jersey.feed.globo;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.function.Consumer;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.dekses.jersey.feed.globo.providers.JsonProvider;
import com.dekses.jersey.feed.globo.providers.XMLProvider;

import converters.Converter;


/**
 * Root resource (exposed at "feed" path)
 */
@Path("feed")
public class FeedResource {

	@DefaultValue("http://revistaautoesporte.globo.com/rss/ultimas/feed.xml") @QueryParam("xmlurl")
	private String url;
	
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
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() throws Exception {
    	
    	XMLProvider xmlprovider = new XMLProvider();
    
    	String xml = xmlprovider.GetXML(url);
   	
    	//PEGA DENTRO DO JSONOBJECT O ARRAY DE ITENS
    	JsonProvider jsonprovider = new JsonProvider();
    	JSONArray itens = jsonprovider.GetArrayDeItens(xml);

    	Converter converter = new Converter();
    	JSONArray feed = converter.ConverteItemsToFeedItem(itens);

    	JSONObject root = new JSONObject();
    	root.put("feed", feed);

    	String t = root.toString() ;
    	//System.out.println(t);
        return t;
    }
}

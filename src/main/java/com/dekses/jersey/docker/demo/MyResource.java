package com.dekses.jersey.docker.demo;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.function.Consumer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws MalformedURLException 
     */ 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() throws Exception {
    	 
    	//AQUI EU COMEÇO A PEGAR O XML
    	String url="http://revistaautoesporte.globo.com/rss/ultimas/feed.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String soapmessageString = writer.getBuffer().toString();
    	//A SAIDA É A VARIAVEL soapmessageString QUE TEM O XML
        
        //AGORA ELE CONVERTE O XML QUE ESTÁ EM UMA STRING PARA UM JSONOBJECT
    	JSONObject soapDatainJsonObject = XML.toJSONObject(soapmessageString);
    	
    	//PEGA DENTRO DO JSONOBJECT O ARRAY DE ITENS
    	JSONArray itens =  soapDatainJsonObject.getJSONObject("rss").getJSONObject("channel").getJSONArray("item");

    	
    	JSONArray feed = new JSONArray();
    	
    	//PARA CADA ITEM ELE VAI MONTAR O NOVO OBJETO
    	for (int i = 0; i < itens.length(); i++) {
    	
    		try {
    			
    			
    			
    			JSONArray description = new JSONArray();
    			
    			
    			//PEGANDO O CAMPO DESCRIPTION PARA REMOVER OS DADOS    			
    			org.jsoup.nodes.Document doc1 = Jsoup.parse(itens.getJSONObject(i).getString("description"));

    			//SELECIONANDO TODOS OS <p>
    			Elements paragrafos = doc1.select("p");
            	for (Element paragrafo : paragrafos) {
            		JSONObject pjson = new JSONObject();
            		pjson.put("type", "text");
            		pjson.put("content", paragrafo.text());
            		description.put(pjson);
                }
            	
            	//SELECIONANDO TODOS OS <div><img>
            	Elements images = doc1.select("div").select("img");
            	for (Element image : images) {
            		JSONObject ijson = new JSONObject();
            		ijson.put("type", "image");
            		ijson.put("content", image.attr("src"));
            		description.put(ijson);
                }
            	
            	//SELECIONANDO TODOS OS <div><ul><li><a>
    			Elements links = doc1.select("div").select("ul").select("li").select("a");
            	for (Element link : links) {
            		JSONObject ljson = new JSONObject();
            		ljson.put("type", "image");
            		ljson.put("content", link.attr("abs:href"));
            		description.put(ljson);
                }
            	
            	
            	org.jsoup.nodes.Document titulo = Jsoup.parse(itens.getJSONObject(i).getString("title"));

            	org.jsoup.nodes.Document link = Jsoup.parse(itens.getJSONObject(i).getString("link"));
            	
    			JSONObject item = new JSONObject();
    			item.put("title", titulo.text());
    			item.put("link", link.text());
    			item.put("description", description);
            	
    			feed.put(item);
    			
            	System.out.println(item.toString());
            	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
    		
    		
        	
    	
    	}
   	
    	String t = feed.toString() ;
    	
        return t;
    }
}

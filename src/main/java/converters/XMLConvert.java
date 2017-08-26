package converters;

import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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

public class XMLConvert {

	public String converteXMLtoJson(String url) throws Exception {
    	
    	//Faz o download do XML e entre ele em String
    	String xml = downloadAsString(url);

    	//Pega a lista de itens do XML em formato JSON
    	JSONArray itens = getArrayDeItens(xml);
    	
    	JSONArray feed = converteItemsToFeedItem(itens);

    	JSONObject root = new JSONObject();
    	root.put("feed", feed);
    	
    	return root.toString();
	}
	
	public JSONArray getArrayDeItens(String xml) {
		 //AGORA ELE CONVERTE O XML QUE ESTÁ EM UMA STRING PARA UM JSONOBJECT
	   	JSONObject soapDatainJsonObject = XML.toJSONObject(xml);
	
	   	//PEGA DENTRO DO JSONOBJECT O ARRAY DE ITENS
	   	JSONArray itens =  soapDatainJsonObject.getJSONObject("rss").getJSONObject("channel").getJSONArray("item");
	   	
	   	return itens;
	}
	
	public String downloadAsString(String url) throws Exception {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        
        return writer.getBuffer().toString();
	}
	
	public JSONArray converteItemsToFeedItem(JSONArray itens) {
		
		JSONArray feed = new JSONArray();
    	
    	//PARA CADA ITEM ELE VAI MONTAR O NOVO OBJETO
    	for (int i = 0; i < itens.length(); i++) {
	
			JSONArray description = new JSONArray();
			
			//PEGANDO O CAMPO DESCRIPTION PARA REMOVER OS DADOS    			
			org.jsoup.nodes.Document doc1 = Jsoup.parse(itens.getJSONObject(i).getString("description"));

			//SELECIONANDO TODOS OS <p>
			Elements paragrafos = doc1.select("p");
        	for (Element paragrafo : paragrafos) {
        		
        		if(!paragrafo.text().equals("\u00a0")) {
        			JSONObject pjson = new JSONObject();
            		pjson.put("type", "text");
            		pjson.put("content", paragrafo.text());
            		description.put(pjson);	
        		}
        		
        		
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
			JSONArray listalinks = new JSONArray();
        	for (Element link : links) {

        		listalinks.put(link.attr("abs:href"));
            }
    		JSONObject ljson = new JSONObject();
    		ljson.put("type", "links");
    		ljson.put("content", listalinks);
    		
        	description.put(ljson);
        	
        	
        	org.jsoup.nodes.Document titulo = Jsoup.parse(itens.getJSONObject(i).getString("title"));
        	org.jsoup.nodes.Document link = Jsoup.parse(itens.getJSONObject(i).getString("link"));
        	
        	
			JSONObject item = new JSONObject();
			item.put("title", titulo.text());
			item.put("link", link.text());
			item.put("description", description);
        	
			JSONObject itemroot = new JSONObject();
			itemroot.put("item", item);
			
			feed.put(itemroot);
    		
    	}
    	
    	return feed;
	}
}

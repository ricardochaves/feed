package converters;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Converter {

	public JSONArray ConverteItemsToFeedItem(JSONArray itens) {
		
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
    	return feed;
	}
}

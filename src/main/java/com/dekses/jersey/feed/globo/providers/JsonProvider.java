package com.dekses.jersey.feed.globo.providers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class JsonProvider {

	public JSONArray GetArrayDeItens(String xml) {
		 //AGORA ELE CONVERTE O XML QUE ESTÁ EM UMA STRING PARA UM JSONOBJECT
    	JSONObject soapDatainJsonObject = XML.toJSONObject(xml);

    	//PEGA DENTRO DO JSONOBJECT O ARRAY DE ITENS
    	JSONArray itens =  soapDatainJsonObject.getJSONObject("rss").getJSONObject("channel").getJSONArray("item");
    	
    	return itens;
	}
}

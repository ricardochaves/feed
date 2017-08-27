package com.feedglobo.interfaces;

import org.json.JSONArray;

/**
 * Inteface criada para injeção de dependência. 
 * @author ricar
 *
 */
public interface IXMLConvert {
	String converteXMLtoJson(String url) throws Exception;
	JSONArray getArrayDeItens(String xml);
	String downloadAsString(String url) throws Exception;
	JSONArray converteItemsToFeedItem(JSONArray itens);
	
}

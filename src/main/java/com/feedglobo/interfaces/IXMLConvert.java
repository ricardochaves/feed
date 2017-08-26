package com.feedglobo.interfaces;

import org.json.JSONArray;

public interface IXMLConvert {
	String converteXMLtoJson(String url) throws Exception;
	JSONArray getArrayDeItens(String xml);
	String downloadAsString(String url) throws Exception;
	JSONArray converteItemsToFeedItem(JSONArray itens);
	
}

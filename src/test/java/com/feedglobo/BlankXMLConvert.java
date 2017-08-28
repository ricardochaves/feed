package com.feedglobo;

import org.json.JSONArray;

import com.feedglobo.interfaces.IXMLConvert;

public class BlankXMLConvert implements IXMLConvert {

    @Override
    public String converteXMLtoJson(String url) throws Exception {
        // TODO Auto-generated method stub
        return "{'status':'sucesso'}";
    }

    @Override
    public JSONArray getArrayDeItens(String xml) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String downloadAsString(String url) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JSONArray converteItemsToFeedItem(JSONArray itens) {
        // TODO Auto-generated method stub
        return null;
    }

}

package com.feedglobo.converters;

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

import com.feedglobo.interfaces.IXMLConvert;

/**
 * Reponsável por toda a conversão do XML até o Json final.
 *
 */

public class XMLConvert implements IXMLConvert {

    /**
     * Faz o download do XML e converte para um JSONObject
     */
    public String converteXMLtoJson(String url) throws Exception {

        // Faz o download do XML e entre ele em String
        String xml = downloadAsString(url);

        // Pega a lista de itens do XML em formato JSON
        JSONArray itens = getArrayDeItens(xml);

        JSONArray feed = converteItemsToFeedItem(itens);

        JSONObject root = new JSONObject();
        root.put("feed", feed);

        return root.toString();
    }

    /**
     * Recebe o XML em forma de String e retira de dentro dele a lista de itens já
     * em formato de JSONArray
     */
    public JSONArray getArrayDeItens(String xml) {
        // Pega o XML em String e converte para JSONObject
        JSONObject soapDatainJsonObject = XML.toJSONObject(xml);

        // Pega dentro do JSONObject o array de itens
        JSONArray itens = soapDatainJsonObject.getJSONObject("rss").getJSONObject("channel").getJSONArray("item");

        return itens;
    }

    /**
     * Recebe uma url e retorna um String com o seu conteúdo. Nesse projet é o
     * proprio XML do feed de origem de dados.
     */
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

    /**
     * Recebe um JSONArray com os itens do XML de origem de dados e converte para um
     * JSONArray de itens no formato de saída
     */
    public JSONArray converteItemsToFeedItem(JSONArray itens) {

        // Criando a lista feed do Json
        JSONArray feed = new JSONArray();

        // Para item na lista de itens original ele vai criar um novo item na lista de
        // saída
        for (int i = 0; i < itens.length(); i++) {

            // Criando lista de itens da descrição.
            JSONArray description = new JSONArray();

            // Pegando o conteúdo da descrição para começar a extrair os dados.
            org.jsoup.nodes.Document doc1 = Jsoup.parse(itens.getJSONObject(i).getString("description"));

            montaListaDeItensDeParagrafo(doc1, description);

            montaListaDeItensDeImagem(doc1, description);

            montaListaDeItensDeLinks(doc1, description);

            // Pegando o titulo e o link do item e colocando no novo objeto
            org.jsoup.nodes.Document titulo = Jsoup.parse(itens.getJSONObject(i).getString("title"));
            org.jsoup.nodes.Document link = Jsoup.parse(itens.getJSONObject(i).getString("link"));

            // Criando um nó item e incluindo os objetos gerados acima, formando assim a
            // estrutura
            // final dos dados de um item.
            JSONObject item = new JSONObject();
            item.put("title", titulo.text());
            item.put("link", link.text());
            item.put("description", description);

            // Incluindo o item final dentro de um JSONObject para finalizar o item.
            JSONObject itemroot = new JSONObject();
            itemroot.put("item", item);

            // Incluindo o item final dentro da lista de itens.
            feed.put(itemroot);

        }

        return feed;
    }

    private void montaListaDeItensDeParagrafo(org.jsoup.nodes.Document doc, JSONArray description) {
        // Selecionando todos os parágrafos (<p>) de dentro da string description
        Elements paragrafos = doc.select("p");
        for (Element paragrafo : paragrafos) {

            // Esse if é para regirar os parágos que tem apenas um enter de conteúdo.
            // Optei por retirar por causa do exemplo de saída de json dado no Git do
            // desafio.
            if (!paragrafo.text().equals("\u00a0")) {
                JSONObject pjson = new JSONObject();
                pjson.put("type", "text");
                pjson.put("content", paragrafo.text());
                description.put(pjson);
            }

        }
    }

    private void montaListaDeItensDeImagem(org.jsoup.nodes.Document doc, JSONArray description) {
        // Selecionando todas as imagens (<img>) que existem dentro de uma <div>
        Elements images = doc.select("div").select("img");
        for (Element image : images) {
            JSONObject ijson = new JSONObject();
            ijson.put("type", "image");
            ijson.put("content", image.attr("src"));
            description.put(ijson);
        }
    }

    private void montaListaDeItensDeLinks(org.jsoup.nodes.Document doc, JSONArray description) {
        // Selecionando todos os links(<a>) dentro da estrutura: <div><ul><li><a>
        Elements links = doc.select("div").select("ul").select("li").select("a");
        JSONArray listalinks = new JSONArray();
        for (Element link : links) {

            listalinks.put(link.attr("abs:href"));
        }

        // Após a lista de links montada ela é incluída dentro de um nó links do json de
        // saída
        JSONObject ljson = new JSONObject();
        ljson.put("type", "links");
        ljson.put("content", listalinks);

        // O novo item (ljson) está pronto e é incluindo como um item da lista
        // description
        description.put(ljson);
    }
}

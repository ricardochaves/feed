package com.feedglobo;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.feedglobo.converters.XMLConvert;
import com.feedglobo.interfaces.IXMLConvert;

/**
 * Main class.
 *
 */
public class Main {
    // URL base para o servidor Web, o 0.0.0.0. é nescessário no lugar de locahost para o 
	// funcionamento da aplicação dentro do conteiner docker 
    public static final String BASE_URI = "http://0.0.0.0:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.feedglobo package
    	// Cria um ResourceConfig onde ele faz um scam dentro do namespace informado
        final ResourceConfig rc = new ResourceConfig().packages("com.feedglobo");
        
        // aqui é registrado a injeção de dependêcia.
        rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(XMLConvert.class).to(IXMLConvert.class);
            }
        });

        // Cria uma instancia e expões o servidor de acordo com a url base e o resouceconfig
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
	public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
	

}


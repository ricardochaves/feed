package com.feedglobo;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.feedglobo.jwt.AjudaAuth;

@Path("/token")
public class AuthenticationResource {
	 @POST
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	    public Response authenticateUser(@FormParam("username") String username, 
	                                     @FormParam("password") String password) {

	        try {

	            // Authenticate the user using the credentials provided
	            authenticate(username, password);

	            // Issue a token for the user
	            String token = issueToken(username);

	            // Return the token on the response
	            return Response.ok(token).build();

	        } catch (Exception e) {
	            return Response.status(Response.Status.FORBIDDEN).build();
	        }      
	    }

	    private void authenticate(String username, String password) throws Exception {
	        if(!username.equals("admin") || !password.equals("admin")) {
	        	throw new Exception("Usuário e/ou senha não existe ou inválido.");
	        }
	    }

	    private String issueToken(String username) {
	    	return AjudaAuth.createJsonWebToken(username, 10);
	    }
}

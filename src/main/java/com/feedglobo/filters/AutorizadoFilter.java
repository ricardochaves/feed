package com.feedglobo.filters;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.feedglobo.jwt.AjudaAuth;
import com.feedglobo.jwt.Autorizado;

/**
 * Filtro executado para verificar se o usuário está logado.
 * Ele trabalha junto com a anotação de Autorizado.
 * @author ricar
 *
 */
@Autorizado
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AutorizadoFilter implements ContainerRequestFilter {
	   
	private static final String AUTHENTICATION_SCHEME = "JWT";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Pega o header de autorização do request.
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Valida o header de autorização
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Pega o token de dentro do header
        String token = authorizationHeader
                            .substring(AUTHENTICATION_SCHEME.length()).trim();

        try {

            // Valida o Token
            validateToken(token);

        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Verifica se o header de autorição é válido.
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                    .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Para a execução do filtro retornando 401
        // O "WWW-Authenticate" é enviado no response.
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME)
                        .build());
    }

    private void validateToken(String token) throws Exception {
        AjudaAuth.verifyToken(token);
    }
}

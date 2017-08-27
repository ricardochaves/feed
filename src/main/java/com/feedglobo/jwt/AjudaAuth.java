package com.feedglobo.jwt;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;

/**
 * Classe com metodos de suporte a autenticação. 
 * Ela é reponsável pro gerar um gerar e verificar um jwt
 *
 */
public class AjudaAuth {

    private static final String AUDIENCE = "MinhaAudienceDeTesteParaGlobo";

    private static final String ISSUER = "FeedGlogoIssuer";

    private static final String SIGNING_KEY = "jkl43j5l4j5lk3j54lj5lk4j3k4j5kl3jdiuf0s98d3m89490375n89347n897&¨¨¨#&#(NN*@NM¨&BN(*¨@(&%&@N(@N*@";

    /**
     * Cria um token da web json que é um token assinado digitalmente que contém 
     * carga útil (por exemplo, userId para identificar o usuário). 
     * A chave de assinatura é secreta. Isso garante que o token seja autêntico e 
     * não tenha sido modificado. O uso de um jwt elimina a necessidade de armazenar 
     * informações de sessão de autenticação em um banco de dados.
     * @param userId
     * @param durationDays
     * @return
     */
    public static String createJsonWebToken(String idUsuario, int diasDuracao)    {
        //Pegando tempo corrente e o algoritico de criptografia.
        Calendar cal = Calendar.getInstance();
        HmacSHA256Signer signer;
        try {
            signer = new HmacSHA256Signer(ISSUER, null, SIGNING_KEY.getBytes());
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        //Configura JSON token
        JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
        token.setAudience(AUDIENCE);
        token.setIssuedAt(new org.joda.time.Instant(cal.getTimeInMillis()));
        token.setExpiration(new org.joda.time.Instant(cal.getTimeInMillis() + 1000L * 60L * 60L * 24L * diasDuracao));

        //Configurar o objeto de request com as informações do item
        JsonObject request = new JsonObject();
        request.addProperty("idUsuario", idUsuario);

        JsonObject payload = token.getPayloadAsJsonObject();
        payload.add("info", request);

        try {
            return token.serializeAndSign();
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifica a validade do token da web json e extrai o id do usuário e outras informações dele. 
     * @param token
     * @return
     * @throws SignatureException
     * @throws InvalidKeyException
     */
    public static Token verifyToken(String token)  
    {
        try {
            final Verifier hmacVerifier = new HmacSHA256Verifier(SIGNING_KEY.getBytes());

            VerifierProvider hmacLocator = new VerifierProvider() {

                @Override
                public List<Verifier> findVerifier(String id, String key){
                    return Lists.newArrayList(hmacVerifier);
                }
            };
            VerifierProviders locators = new VerifierProviders();
            locators.setVerifierProvider(SignatureAlgorithm.HS256, hmacLocator);
            net.oauth.jsontoken.Checker checker = new net.oauth.jsontoken.Checker(){

                @Override
                public void check(JsonObject payload) throws SignatureException {

                }

            };
            
            JsonTokenParser parser = new JsonTokenParser(locators,
                    checker);
            JsonToken jt;
            try {
                jt = parser.verifyAndDeserialize(token);
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            }
            JsonObject payload = jt.getPayloadAsJsonObject();
            Token t = new Token();
            String issuer = payload.getAsJsonPrimitive("iss").getAsString();
            String userIdString =  payload.getAsJsonObject("info").getAsJsonPrimitive("idUsuario").getAsString();
            if (issuer.equals(ISSUER) && !userIdString.isEmpty())
            {
                t.setUserId(userIdString);
                t.setIssued(new DateTime(payload.getAsJsonPrimitive("iat").getAsLong()));
                t.setExpires(new DateTime(payload.getAsJsonPrimitive("exp").getAsLong()));
                return t;
            }
            else
            {
                return null;
            }
        } catch (InvalidKeyException e1) {
            throw new RuntimeException(e1);
        }
    }

}

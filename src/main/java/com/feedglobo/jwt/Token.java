package com.feedglobo.jwt;

import org.joda.time.DateTime;

/**
 * Objeto que contem os dados do Token
 *
 */
public class Token {
    private String idUsuario;
    private DateTime issued;
    private DateTime expires;
    public String getIdUsuario() {
        return idUsuario;
    }
    public void setUserId(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    public DateTime getIssued() {
        return issued;
    }
    public void setIssued(DateTime issued) {
        this.issued = issued;
    }
    public DateTime getExpires() {
        return expires;
    }
    public void setExpires(DateTime expires) {
        this.expires = expires;
    }
}

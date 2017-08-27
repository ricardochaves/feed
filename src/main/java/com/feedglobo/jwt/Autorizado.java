package com.feedglobo.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

/**
 * Essa é a interface usada para dizer que o metodo ou 
 * 	classe tem apenas acesso a pessoa logadas.
 */

@NameBinding
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Autorizado { }

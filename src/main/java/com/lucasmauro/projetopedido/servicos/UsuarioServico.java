package com.lucasmauro.projetopedido.servicos;

import org.springframework.security.core.context.SecurityContextHolder;

import com.lucasmauro.projetopedido.security.UsuarioSS;

public class UsuarioServico {

	public static UsuarioSS authenticated() {
		try {
			return (UsuarioSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}

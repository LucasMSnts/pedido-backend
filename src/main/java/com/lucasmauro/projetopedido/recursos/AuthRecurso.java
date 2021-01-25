package com.lucasmauro.projetopedido.recursos;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmauro.projetopedido.security.JWTUtil;
import com.lucasmauro.projetopedido.security.UsuarioSS;
import com.lucasmauro.projetopedido.servicos.UsuarioServico;

@RestController
@RequestMapping(value = "/auth")
public class AuthRecurso {
	
	@Autowired
	private JWTUtil jwtUtil;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UsuarioSS usuario = UsuarioServico.authenticated();
		String token = jwtUtil.generateToken(usuario.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
}

package com.lucasmauro.projetopedido.recursos;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmauro.projetopedido.dto.EmailDTO;
import com.lucasmauro.projetopedido.security.JWTUtil;
import com.lucasmauro.projetopedido.security.UsuarioSS;
import com.lucasmauro.projetopedido.servicos.AuthServico;
import com.lucasmauro.projetopedido.servicos.UsuarioServico;

@RestController
@RequestMapping(value = "/auth")
public class AuthRecurso {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthServico servico;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UsuarioSS usuario = UsuarioServico.authenticated();
		String token = jwtUtil.generateToken(usuario.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		servico.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}

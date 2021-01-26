package com.lucasmauro.projetopedido.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="Preenchimento obrigatorio")
	@Email(message="Email invalido")
	private String email;
	
	public EmailDTO() {
		
	}

	public EmailDTO(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
}

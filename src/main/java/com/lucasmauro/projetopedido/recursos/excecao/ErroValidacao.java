package com.lucasmauro.projetopedido.recursos.excecao;

import java.util.ArrayList;
import java.util.List;

public class ErroValidacao extends ErroPadrao {	
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> erros = new ArrayList<>();
	
	public ErroValidacao(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErros() {
		return erros;
	}
	
	public void addError(String fieldName, String mensagem) {
		erros.add(new FieldMessage(fieldName, mensagem));
	}
}

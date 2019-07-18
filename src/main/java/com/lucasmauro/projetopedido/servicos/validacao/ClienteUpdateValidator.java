package com.lucasmauro.projetopedido.servicos.validacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.lucasmauro.projetopedido.dominio.Cliente;
import com.lucasmauro.projetopedido.dto.ClienteDTO;
import com.lucasmauro.projetopedido.recursos.excecao.FieldMessage;
import com.lucasmauro.projetopedido.repositorios.ClienteRepositorio;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepositorio repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDTO, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));		
		
		List<FieldMessage> lista = new ArrayList<>();
				
		Cliente aux = repo.findByEmail(objDTO.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
			lista.add(new FieldMessage("email", "Email já existente!"));
		}
		
		for (FieldMessage e : lista) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return lista.isEmpty();
	}
}

package com.lucasmauro.projetopedido.servicos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailServico extends AbstractEmailServico {
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailServico.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString());
		LOG.info("Email enviado");
	}

}

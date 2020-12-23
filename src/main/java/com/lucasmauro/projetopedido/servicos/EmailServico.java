package com.lucasmauro.projetopedido.servicos;

import org.springframework.mail.SimpleMailMessage;

import com.lucasmauro.projetopedido.dominio.Pedido;

public interface EmailServico {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}

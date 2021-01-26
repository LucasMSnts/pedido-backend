package com.lucasmauro.projetopedido.servicos;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.lucasmauro.projetopedido.dominio.Cliente;
import com.lucasmauro.projetopedido.dominio.Pedido;

public interface EmailServico {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}

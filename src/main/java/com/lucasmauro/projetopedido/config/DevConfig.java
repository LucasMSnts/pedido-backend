package com.lucasmauro.projetopedido.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lucasmauro.projetopedido.servicos.DBServico;
import com.lucasmauro.projetopedido.servicos.EmailServico;
import com.lucasmauro.projetopedido.servicos.SmtpEmailServico;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBServico dbServico;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if (!"create".equals(strategy)) {
			return false;
		}
		
		dbServico.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailServico emailService() {
		return new SmtpEmailServico();
	}
}

package com.lucasmauro.projetopedido.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lucasmauro.projetopedido.servicos.DBServico;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBServico dbServico;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbServico.instantiateTestDatabase();
		return true;
	}
}

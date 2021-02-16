package com.lucasmauro.projetopedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasmauro.projetopedido.servicos.S3Servico;

@SpringBootApplication
public class ProjetopedidoApplication implements CommandLineRunner{
	
	@Autowired
	private S3Servico s3Servico;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetopedidoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Servico.uploadFile("C:\\Users\\lucas\\Pictures\\Papyros2.jpg");		
	}

}

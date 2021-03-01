package com.lucasmauro.projetopedido.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmauro.projetopedido.dominio.Estado;
import com.lucasmauro.projetopedido.repositorios.EstadoRepositorio;

@Service
public class EstadoServico {

	@Autowired
	private EstadoRepositorio repo;
	
	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}
}

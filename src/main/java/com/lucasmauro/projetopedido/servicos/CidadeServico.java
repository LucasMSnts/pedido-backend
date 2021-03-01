package com.lucasmauro.projetopedido.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmauro.projetopedido.dominio.Cidade;
import com.lucasmauro.projetopedido.repositorios.CidadeRepositorio;

@Service
public class CidadeServico {

	@Autowired
	private CidadeRepositorio repo;
	
	public List<Cidade> findByEstado(Integer estadoId) {
		return repo.findCidades(estadoId);
	}
}

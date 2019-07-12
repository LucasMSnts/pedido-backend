package com.lucasmauro.projetopedido.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmauro.projetopedido.dominio.Pedido;
import com.lucasmauro.projetopedido.repositorios.PedidoRepositorio;
import com.lucasmauro.projetopedido.servicos.excecoes.ObjectNotFoundException;

@Service
public class PedidoServico {

	@Autowired
	private PedidoRepositorio repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}	
}

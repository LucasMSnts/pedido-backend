package com.lucasmauro.projetopedido.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmauro.projetopedido.dominio.Pedido;
import com.lucasmauro.projetopedido.servicos.PedidoServico;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoRecurso {
	
	@Autowired
	private PedidoServico servico;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {			
		Pedido obj = servico.find(id);
		return ResponseEntity.ok().body(obj);		
	}
}

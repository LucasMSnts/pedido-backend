package com.lucasmauro.projetopedido.recursos;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){
		obj = servico.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value="pagina", defaultValue="0") Integer page, 
			@RequestParam(value="linhas", defaultValue="24") Integer linhasPorPaginas, 
			@RequestParam(value="ordenar", defaultValue="instante") String orderBy, 
			@RequestParam(value="direcao", defaultValue="DESC") String direcao) {			
		Page<Pedido> lista = servico.findPage(page, linhasPorPaginas, orderBy, direcao);
		return ResponseEntity.ok().body(lista);		
	}
}

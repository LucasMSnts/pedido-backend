package com.lucasmauro.projetopedido.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmauro.projetopedido.dominio.Categoria;
import com.lucasmauro.projetopedido.servicos.CategoriaServico;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaRecurso {
	
	@Autowired
	private CategoriaServico servico;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {			
		Categoria obj = servico.buscar(id);
		return ResponseEntity.ok().body(obj);		
	}
}

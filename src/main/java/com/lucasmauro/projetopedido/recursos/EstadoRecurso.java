package com.lucasmauro.projetopedido.recursos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmauro.projetopedido.dominio.Cidade;
import com.lucasmauro.projetopedido.dominio.Estado;
import com.lucasmauro.projetopedido.dto.CidadeDTO;
import com.lucasmauro.projetopedido.dto.EstadoDTO;
import com.lucasmauro.projetopedido.servicos.CidadeServico;
import com.lucasmauro.projetopedido.servicos.EstadoServico;


@RestController
@RequestMapping(value = "/estados")
public class EstadoRecurso {

	@Autowired
	private EstadoServico servico;
	
	@Autowired
	private CidadeServico cidadeServico;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> lista = servico.findAll();
		List<EstadoDTO> listaDTO = lista.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
		List<Cidade> lista = cidadeServico.findByEstado(estadoId);
		List<CidadeDTO> listaDTO = lista.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
}

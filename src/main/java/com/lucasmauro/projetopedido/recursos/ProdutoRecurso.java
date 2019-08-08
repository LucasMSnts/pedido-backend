package com.lucasmauro.projetopedido.recursos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmauro.projetopedido.dominio.Produto;
import com.lucasmauro.projetopedido.dto.ProdutoDTO;
import com.lucasmauro.projetopedido.recursos.utils.URL;
import com.lucasmauro.projetopedido.servicos.ProdutoServico;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoRecurso {
	
	@Autowired
	private ProdutoServico servico;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {			
		Produto obj = servico.find(id);
		return ResponseEntity.ok().body(obj);		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="pagina", defaultValue="0") Integer page, 
			@RequestParam(value="linhas", defaultValue="24") Integer linhasPorPaginas, 
			@RequestParam(value="ordenar", defaultValue="nome") String orderBy, 
			@RequestParam(value="direcao", defaultValue="ASC") String direcao) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> lista = servico.search(nomeDecoded, ids, page, linhasPorPaginas, orderBy, direcao);
		Page<ProdutoDTO> listaDTO = lista.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listaDTO);		
	}
}

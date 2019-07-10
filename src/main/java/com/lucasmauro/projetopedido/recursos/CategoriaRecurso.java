package com.lucasmauro.projetopedido.recursos;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.lucasmauro.projetopedido.dominio.Categoria;
import com.lucasmauro.projetopedido.dto.CategoriaDTO;
import com.lucasmauro.projetopedido.servicos.CategoriaServico;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaRecurso {
	
	@Autowired
	private CategoriaServico servico;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {			
		Categoria obj = servico.find(id);
		return ResponseEntity.ok().body(obj);		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = servico.fromDTO(objDTO);
		obj = servico.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id) {
		Categoria obj = servico.fromDTO(objDTO);
		obj.setId(id);
		obj = servico.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		servico.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {			
		List<Categoria> lista = servico.findAll();
		List<CategoriaDTO> listaDTO = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);		
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="pagina", defaultValue="0") Integer page, 
			@RequestParam(value="linhas", defaultValue="24") Integer linhasPorPaginas, 
			@RequestParam(value="ordenar", defaultValue="nome") String orderBy, 
			@RequestParam(value="direcao", defaultValue="ASC") String direcao) {			
		Page<Categoria> lista = servico.findPage(page, linhasPorPaginas, orderBy, direcao);
		Page<CategoriaDTO> listaDTO = lista.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listaDTO);		
	}
}

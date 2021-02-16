package com.lucasmauro.projetopedido.recursos;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasmauro.projetopedido.dominio.Cliente;
import com.lucasmauro.projetopedido.dto.ClienteDTO;
import com.lucasmauro.projetopedido.dto.ClienteNovoDTO;
import com.lucasmauro.projetopedido.servicos.ClienteServico;

@RestController
@RequestMapping(value="/clientes")
public class ClienteRecurso {
	
	@Autowired
	private ClienteServico servico;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {			
		Cliente obj = servico.find(id);
		return ResponseEntity.ok().body(obj);		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNovoDTO objDto) {
		Cliente obj = servico.fromDTO(objDto);
		obj = servico.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		Cliente obj = servico.fromDTO(objDTO);
		obj.setId(id);
		obj = servico.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		servico.delete(id);
		return ResponseEntity.noContent().build();
	}
		
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {			
		List<Cliente> lista = servico.findAll();
		List<ClienteDTO> listaDTO = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="pagina", defaultValue="0") Integer page, 
			@RequestParam(value="linhas", defaultValue="24") Integer linhasPorPaginas, 
			@RequestParam(value="ordenar", defaultValue="nome") String orderBy, 
			@RequestParam(value="direcao", defaultValue="ASC") String direcao) {			
		Page<Cliente> lista = servico.findPage(page, linhasPorPaginas, orderBy, direcao);
		Page<ClienteDTO> listaDTO = lista.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listaDTO);		
	}
	
	@RequestMapping(value="/picture", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
		URI uri = servico.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();		
	}
}

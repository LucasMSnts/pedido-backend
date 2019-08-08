package com.lucasmauro.projetopedido.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucasmauro.projetopedido.dominio.Categoria;
import com.lucasmauro.projetopedido.dominio.Produto;
import com.lucasmauro.projetopedido.repositorios.CategoriaRepositorio;
import com.lucasmauro.projetopedido.repositorios.ProdutoRepositorio;
import com.lucasmauro.projetopedido.servicos.excecoes.ObjectNotFoundException;

@Service
public class ProdutoServico {

	@Autowired
	private ProdutoRepositorio repo;
	
	@Autowired
	private CategoriaRepositorio categoriaRepo;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linhasPorPaginas, String orderBy, String direcao) { 
		PageRequest pageRequest = PageRequest.of(page, linhasPorPaginas, Direction.valueOf(direcao), orderBy);
		List<Categoria> categorias = categoriaRepo.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}

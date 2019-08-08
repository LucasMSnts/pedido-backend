package com.lucasmauro.projetopedido.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucasmauro.projetopedido.dominio.Categoria;
import com.lucasmauro.projetopedido.dominio.Produto;

@Repository
public interface ProdutoRepositorio extends JpaRepository<Produto, Integer>{

	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome,@Param("categorias") List<Categoria> categorias, Pageable pageRequest);
}

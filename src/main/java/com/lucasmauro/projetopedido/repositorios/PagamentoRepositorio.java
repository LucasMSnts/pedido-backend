package com.lucasmauro.projetopedido.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmauro.projetopedido.dominio.Pagamento;

@Repository
public interface PagamentoRepositorio extends JpaRepository<Pagamento, Integer>{

}

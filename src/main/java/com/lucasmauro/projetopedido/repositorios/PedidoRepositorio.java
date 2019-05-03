package com.lucasmauro.projetopedido.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmauro.projetopedido.dominio.Pedido;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Integer>{

}

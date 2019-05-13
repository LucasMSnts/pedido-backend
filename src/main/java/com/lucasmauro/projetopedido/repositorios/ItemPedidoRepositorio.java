package com.lucasmauro.projetopedido.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmauro.projetopedido.dominio.ItemPedido;

@Repository
public interface ItemPedidoRepositorio extends JpaRepository<ItemPedido, Integer>{

}

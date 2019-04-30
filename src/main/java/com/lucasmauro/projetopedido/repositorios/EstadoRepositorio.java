package com.lucasmauro.projetopedido.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmauro.projetopedido.dominio.Estado;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Integer>{

}

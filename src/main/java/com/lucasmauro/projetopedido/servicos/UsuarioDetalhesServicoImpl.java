package com.lucasmauro.projetopedido.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lucasmauro.projetopedido.dominio.Cliente;
import com.lucasmauro.projetopedido.repositorios.ClienteRepositorio;
import com.lucasmauro.projetopedido.security.UsuarioSS;

@Service
public class UsuarioDetalhesServicoImpl implements UserDetailsService {
	
	@Autowired
	private ClienteRepositorio repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = repo.findByEmail(email);
		
		if(cli == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UsuarioSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}

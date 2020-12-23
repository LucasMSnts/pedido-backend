package com.lucasmauro.projetopedido.servicos;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasmauro.projetopedido.dominio.ItemPedido;
import com.lucasmauro.projetopedido.dominio.PagamentoComBoleto;
import com.lucasmauro.projetopedido.dominio.Pedido;
import com.lucasmauro.projetopedido.dominio.enums.EstadoPagamento;
import com.lucasmauro.projetopedido.repositorios.ItemPedidoRepositorio;
import com.lucasmauro.projetopedido.repositorios.PagamentoRepositorio;
import com.lucasmauro.projetopedido.repositorios.PedidoRepositorio;
import com.lucasmauro.projetopedido.servicos.excecoes.ObjectNotFoundException;

@Service
public class PedidoServico {

	@Autowired
	private PedidoRepositorio repo;
	
	@Autowired
	private BoletoServico boletoServico;
	
	@Autowired
	private PagamentoRepositorio pagamentoRepositorio;
	
	@Autowired
	private ProdutoServico produtoServico;
	
	@Autowired
	private ItemPedidoRepositorio itemPedidoRepositorio;
	
	@Autowired
	private ClienteServico clienteServico;
	
	@Autowired
	private EmailServico emailServico;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteServico.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoServico.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepositorio.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoServico.find(ip.getProduto().getId())); 
			ip.setPreco(ip.getProduto().getPreco()); 
			ip.setPedido(obj);
		}
		itemPedidoRepositorio.saveAll(obj.getItens());
		emailServico.sendOrderConfirmationEmail(obj);
		return obj;
	}
}

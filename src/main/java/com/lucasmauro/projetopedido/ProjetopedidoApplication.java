package com.lucasmauro.projetopedido;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucasmauro.projetopedido.dominio.Categoria;
import com.lucasmauro.projetopedido.dominio.Cidade;
import com.lucasmauro.projetopedido.dominio.Cliente;
import com.lucasmauro.projetopedido.dominio.Endereco;
import com.lucasmauro.projetopedido.dominio.Estado;
import com.lucasmauro.projetopedido.dominio.ItemPedido;
import com.lucasmauro.projetopedido.dominio.Pagamento;
import com.lucasmauro.projetopedido.dominio.PagamentoComBoleto;
import com.lucasmauro.projetopedido.dominio.PagamentoComCartao;
import com.lucasmauro.projetopedido.dominio.Pedido;
import com.lucasmauro.projetopedido.dominio.Produto;
import com.lucasmauro.projetopedido.dominio.enums.EstadoPagamento;
import com.lucasmauro.projetopedido.dominio.enums.TipoCliente;
import com.lucasmauro.projetopedido.repositorios.CategoriaRepositorio;
import com.lucasmauro.projetopedido.repositorios.CidadeRepositorio;
import com.lucasmauro.projetopedido.repositorios.ClienteRepositorio;
import com.lucasmauro.projetopedido.repositorios.EnderecoRepositorio;
import com.lucasmauro.projetopedido.repositorios.EstadoRepositorio;
import com.lucasmauro.projetopedido.repositorios.ItemPedidoRepositorio;
import com.lucasmauro.projetopedido.repositorios.PagamentoRepositorio;
import com.lucasmauro.projetopedido.repositorios.PedidoRepositorio;
import com.lucasmauro.projetopedido.repositorios.ProdutoRepositorio;

@SpringBootApplication
public class ProjetopedidoApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepositorio categoriaRepo;
	@Autowired
	private ProdutoRepositorio produtoRepo;
	@Autowired
	private EstadoRepositorio estadoRepo;
	@Autowired
	private CidadeRepositorio cidadeRepo;
	@Autowired
	private ClienteRepositorio clienteRepo;
	@Autowired
	private EnderecoRepositorio enderecoRepo;
	@Autowired
	private PedidoRepositorio pedidoRepo;
	@Autowired
	private PagamentoRepositorio pagamentoRepo;
	@Autowired
	private ItemPedidoRepositorio itemPedidoRepo;

	public static void main(String[] args) {
		SpringApplication.run(ProjetopedidoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Sala de estar");
		Categoria cat4 = new Categoria(null, "Banheiro");
		Categoria cat5 = new Categoria(null, "Quintal");
		Categoria cat6 = new Categoria(null, "Quarto");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));	
		
		
		categoriaRepo.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6));
		produtoRepo.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Rio de Janeiro");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Rio de Janeiro", est1);
		Cidade c2 = new Cidade(null, "Santos", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepo.saveAll(Arrays.asList(est1, est2));
		cidadeRepo.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "José Souza", "jose@gmail.com", "36594826781", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("35894569", "995682294"));
		
		Endereco e1 = new Endereco(null, "Rua Campinas", "45", "Apto 12", "Macuco", "48562078", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Santos", "13", "Apto 13", "Centro", "48568778", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepo.saveAll(Arrays.asList(cli1));
		enderecoRepo.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/10/2010 15:34"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("11/11/2010 10:29"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/12/2010 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepo.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepo.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepo.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}

}

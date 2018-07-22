package com.viniciosrodrigues.cursomc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.viniciosrodrigues.cursomc.domain.Categoria;
import com.viniciosrodrigues.cursomc.domain.Cidade;
import com.viniciosrodrigues.cursomc.domain.Cliente;
import com.viniciosrodrigues.cursomc.domain.Endereco;
import com.viniciosrodrigues.cursomc.domain.Estado;
import com.viniciosrodrigues.cursomc.domain.ItemPedido;
import com.viniciosrodrigues.cursomc.domain.Pagamento;
import com.viniciosrodrigues.cursomc.domain.PagamentoComBoleto;
import com.viniciosrodrigues.cursomc.domain.PagamentoComCartao;
import com.viniciosrodrigues.cursomc.domain.Pedido;
import com.viniciosrodrigues.cursomc.domain.Produto;
import com.viniciosrodrigues.cursomc.domain.enums.EstadoPagamento;
import com.viniciosrodrigues.cursomc.domain.enums.TipoCliente;
import com.viniciosrodrigues.cursomc.repository.CategoriaRepository;
import com.viniciosrodrigues.cursomc.repository.CidadeRepository;
import com.viniciosrodrigues.cursomc.repository.ClienteRepository;
import com.viniciosrodrigues.cursomc.repository.EnderecoRepository;
import com.viniciosrodrigues.cursomc.repository.EstadoRepository;
import com.viniciosrodrigues.cursomc.repository.ItemPedidoRepository;
import com.viniciosrodrigues.cursomc.repository.PagamentoRepository;
import com.viniciosrodrigues.cursomc.repository.PedidoRepository;
import com.viniciosrodrigues.cursomc.repository.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiateTestDatabase() throws ParseException {
		// Cria as categorias
		Categoria catUm = new Categoria(null, "Informática");
		Categoria catDois = new Categoria(null, "Escritório");
		Categoria catTres = new Categoria(null, "Cama, mesa e banho");
		Categoria catQuatro = new Categoria(null, "Eletrônicos");
		Categoria catCinco = new Categoria(null, "Jardinagem");
		Categoria catSeis = new Categoria(null, "Decoração");
		Categoria catSete = new Categoria(null, "Perfumaria");

		// Cria os produtos
		Produto produtoUm = new Produto(null, "Computador", 2000D);
		Produto produtoDois = new Produto(null, "Impressora", 800D);
		Produto produtoTres = new Produto(null, "Mouse", 80D);
		Produto produtoQuatro = new Produto(null, "Mesa de Escritório", 300D);
		Produto produtoCinco = new Produto(null, "Mesa de Escritório", 50D);
		Produto produtoSeis = new Produto(null, "Mesa de Escritório", 200D);
		Produto produtoSete = new Produto(null, "Mesa de Escritório", 120D);
		Produto produtoOito = new Produto(null, "Mesa de Escritório", 800D);
		Produto produtoNove = new Produto(null, "Mesa de Escritório", 100D);
		Produto produtoDez = new Produto(null, "Mesa de Escritório", 180D);
		Produto produtoOnze = new Produto(null, "Mesa de Escritório", 900D);

		// Adiciona os produtos às categorias
		catUm.getProdutos().addAll(Arrays.asList(produtoUm, produtoDois, produtoTres));
		catDois.getProdutos().addAll(Arrays.asList(produtoDois, produtoQuatro));
		catTres.getProdutos().addAll(Arrays.asList(produtoCinco, produtoSeis));
		catQuatro.getProdutos().addAll(Arrays.asList(produtoUm, produtoDois, produtoTres, produtoSete));
		catCinco.getProdutos().add(produtoOito);
		catSeis.getProdutos().addAll(Arrays.asList(produtoNove, produtoDez));
		catSete.getProdutos().add(produtoOnze);

		// Adiciona as categorias aos produtos
		produtoUm.getCategorias().addAll(Arrays.asList(catUm));
		produtoDois.getCategorias().addAll(Arrays.asList(catUm, catDois));
		produtoTres.getCategorias().addAll(Arrays.asList(catUm));
		produtoQuatro.getCategorias().addAll(Arrays.asList(catDois));
		produtoCinco.getCategorias().addAll(Arrays.asList(catTres));
		produtoSeis.getCategorias().addAll(Arrays.asList(catTres));
		produtoSete.getCategorias().addAll(Arrays.asList(catQuatro));
		produtoOito.getCategorias().addAll(Arrays.asList(catCinco));
		produtoNove.getCategorias().addAll(Arrays.asList(catSeis));
		produtoDez.getCategorias().addAll(Arrays.asList(catSeis));
		produtoOnze.getCategorias().addAll(Arrays.asList(catSete));

		// Salva Categorias e Produtos na base de dados
		categoriaRepository.saveAll(Arrays.asList(catUm, catDois, catTres, catQuatro, catCinco, catSeis, catSete));
		produtoRepository.saveAll(Arrays.asList(produtoUm, produtoDois, produtoTres, produtoQuatro, produtoCinco,
				produtoSeis, produtoSete, produtoOito, produtoNove, produtoDez, produtoOnze));

		// Cria os Estados
		Estado estadoUm = new Estado(null, "Pernambuco");
		Estado estadoDois = new Estado(null, "São Paulo");

		// Cria as Cidades
		Cidade cidadeUm = new Cidade(null, "Recife", estadoUm);
		Cidade cidadeDois = new Cidade(null, "São Paulo", estadoDois);
		Cidade cidadeTres = new Cidade(null, "Campinas", estadoDois);

		// Adiciona as Cidades aos Estados
		estadoUm.getCidades().addAll(Arrays.asList(cidadeUm));
		estadoDois.getCidades().addAll(Arrays.asList(cidadeDois, cidadeTres));

		// Salva as Cidades e os Estados
		estadoRepository.saveAll(Arrays.asList(estadoUm, estadoDois));
		cidadeRepository.saveAll(Arrays.asList(cidadeUm, cidadeDois, cidadeTres));

		// Cria os Clientes e Endereços dos clientes
		Cliente clienteUm = new Cliente(null, "Vinícios Rodrigues", "viniciosarodrigues@gmail.com", "08911768456",
				TipoCliente.PESSOAFISICA, pe.encode("123"));
		clienteUm.getTelefones().addAll(Arrays.asList("34360275", "999509300"));

		Endereco enderecoUm = new Endereco(null, "Av. Viconde de Suassun", "874", "Apt. 1102", "Santo Amaro",
				"50050540", clienteUm, cidadeUm);
		Endereco enderecoDois = new Endereco(null, "Rua Professor Augusto Lins e Silva", "621", "Apt. 801",
				"Boa Viagem", "51050500", clienteUm, cidadeUm);

		Cliente clienteDois = new Cliente(null, "GymFitSmart", "maria.silva@gfsmart.com", "99490136000194",
				TipoCliente.PESSOAJURIDICA, pe.encode("123"));
		Endereco enderecoTres = new Endereco(null, "Rua do teste", "123", "Sem complemento", "Bairro Tal", "12345678",
				clienteDois, cidadeDois);

		// Adiciona os Endereços aos Clientes
		clienteUm.getEnderecos().addAll(Arrays.asList(enderecoUm, enderecoDois));
		clienteDois.getEnderecos().addAll(Arrays.asList(enderecoTres));

		// Salva os Clientes e Endereços
		clienteRepository.saveAll(Arrays.asList(clienteUm, clienteDois));
		enderecoRepository.saveAll(Arrays.asList(enderecoUm, enderecoDois, enderecoTres));

		// Cria Pedidos
		SimpleDateFormat stf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido pedUm = new Pedido(null, stf.parse("30/09/2017 10:32"), clienteUm, enderecoUm);
		Pedido pedDois = new Pedido(null, stf.parse("10/10/2017 19:35"), clienteDois, enderecoTres);

		// Cria os pagamentos e adiciona aos pedidos
		Pagamento pagamentoUm = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedUm, 5);
		pedUm.setPagamento(pagamentoUm);

		Pagamento pagamentoDois = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedDois,
				stf.parse("24/06/2018 16:31"), null);
		pedDois.setPagamento(pagamentoDois);

		clienteUm.getPedidos().add(pedUm);
		clienteDois.getPedidos().add(pedDois);

		// Salva os pedidos e pagamentos
		pedidoRepository.saveAll(Arrays.asList(pedUm, pedDois));
		pagamentoRepository.saveAll(Arrays.asList(pagamentoUm, pagamentoDois));

		// Criando itens de pedidos
		ItemPedido ipUm = new ItemPedido(pedUm, produtoUm, 0.0, 1, 2000.0);
		ItemPedido ipDois = new ItemPedido(pedUm, produtoTres, 0.0, 2, 80.0);
		ItemPedido ipTres = new ItemPedido(pedDois, produtoDois, 100.0, 1, 800.0);

		// Adicionando Itends de pedidos aos pedidos
		pedUm.getItens().addAll(Arrays.asList(ipUm, ipDois));
		pedDois.getItens().addAll(Arrays.asList(ipTres));

		// Interligando os Itens do Pedido aos Produtos
		produtoUm.getItens().add(ipUm);
		produtoDois.getItens().add(ipTres);
		produtoTres.getItens().add(ipDois);

		// Salvando itens do pedido
		itemPedidoRepository.saveAll(Arrays.asList(ipUm, ipDois, ipTres));
	}
}

package com.viniciosrodrigues.cursomc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viniciosrodrigues.cursomc.domain.ItemPedido;
import com.viniciosrodrigues.cursomc.domain.PagamentoComBoleto;
import com.viniciosrodrigues.cursomc.domain.Pedido;
import com.viniciosrodrigues.cursomc.domain.enums.EstadoPagamento;
import com.viniciosrodrigues.cursomc.exception.ObjectNotFoundException;
import com.viniciosrodrigues.cursomc.repository.ItemPedidoRepository;
import com.viniciosrodrigues.cursomc.repository.PagamentoRepository;
import com.viniciosrodrigues.cursomc.repository.PedidoRepository;

/**
 * Camada de serviços responsável pela manutenção dos pedidos
 * 
 * @author vinicios.rodrigues
 * 
 * @since 14/07/2018
 * 
 * @see Pedido
 * @see PedidoRepository
 *
 */
@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private BoletoService boletoService;

	public List<Pedido> getListAll() {
		return pedidoRepository.findAll();
	}

	public Pedido findById(Long id) {
		Optional<Pedido> categoriaSalva = pedidoRepository.findById(id);
		return categoriaSalva.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamento = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamento, obj.getInstante());
		}
		pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.findById(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}

}

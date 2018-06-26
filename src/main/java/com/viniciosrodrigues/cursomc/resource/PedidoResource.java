package com.viniciosrodrigues.cursomc.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viniciosrodrigues.cursomc.domain.Pedido;
import com.viniciosrodrigues.cursomc.service.PedidoService;

@RestController
@RequestMapping("pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping
	public ResponseEntity<List<Pedido>> getListAll() {
		return ResponseEntity.status(HttpStatus.OK).body(pedidoService.getListAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> getOne(@PathVariable Long id) {
		Pedido pedidoEncontrada = pedidoService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(pedidoEncontrada);
	}

}

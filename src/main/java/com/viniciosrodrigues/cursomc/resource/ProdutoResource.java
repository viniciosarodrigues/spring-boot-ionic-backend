package com.viniciosrodrigues.cursomc.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viniciosrodrigues.cursomc.domain.Produto;
import com.viniciosrodrigues.cursomc.dto.ProdutoDTO;
import com.viniciosrodrigues.cursomc.resource.utils.URL;
import com.viniciosrodrigues.cursomc.service.ProdutoService;

@RestController
@RequestMapping("produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getOne(@PathVariable Long id) {
		Produto produtoEncontrado = produtoService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(produtoEncontrado);
	}

	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<ProdutoDTO> lista = produtoService
				.search(URL.decodeParam(nome), URL.decodeLongList(categorias), page, linesPerPage, orderBy, direction)
				.map(x -> new ProdutoDTO(x));
		return ResponseEntity.ok().body(lista);
	}
}

package com.viniciosrodrigues.cursomc.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viniciosrodrigues.cursomc.domain.Cliente;
import com.viniciosrodrigues.cursomc.dto.ClienteDTO;
import com.viniciosrodrigues.cursomc.service.ClienteService;

@RestController
@RequestMapping("clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> getListAll() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(clienteService.getListAll().stream().map(x -> new ClienteDTO(x)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Cliente clienteEncontrado = clienteService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(clienteEncontrado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO obj, @PathVariable Long id) {
		obj.setId(id);
		clienteService.update(clienteService.fromDTO(obj));
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping({ "/page" })
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<ClienteDTO> lista = clienteService.findPage(page, linesPerPage, orderBy, direction)
				.map(x -> new ClienteDTO(x));
		return ResponseEntity.ok().body(lista);
	}
}

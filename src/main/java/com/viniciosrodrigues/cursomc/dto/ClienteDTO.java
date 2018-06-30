package com.viniciosrodrigues.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.viniciosrodrigues.cursomc.domain.Cliente;
import com.viniciosrodrigues.cursomc.service.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1044024673546872507L;

	private Long id;

	@NotEmpty(message = "O nome é obrigatório")
	@Size(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@NotEmpty(message = "O nome é obrigatório")
	@Email(message = "Email inválido")
	private String email;

	public ClienteDTO() {
		super();
	}

	public ClienteDTO(Cliente cliente) {
		super();
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

package com.viniciosrodrigues.cursomc.dto;

import java.io.Serializable;

import com.viniciosrodrigues.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 5364227411647654906L;

	private Long id;

	private String nome;

	private Double preco;

	public ProdutoDTO() {
		super();
	}

	public ProdutoDTO(Produto p) {
		super();
		this.id = p.getId();
		this.nome = p.getNome();
		this.preco = p.getPreco();
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

}

package com.viniciosrodrigues.cursomc.dto;

import java.io.Serializable;

public class ClienteNewDTO implements Serializable {

	private static final long serialVersionUID = 6151574439970627716L;
	private String nome;
	private String email;
	private String cpfOuCnpj;

	private Integer tipo;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;

	private String telefoneUm;
	private String telefoneDois;
	private String telefoneTres;

	private Long cidadeId;

	public ClienteNewDTO() {
		super();
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefoneUm() {
		return telefoneUm;
	}

	public void setTelefoneUm(String telefoneUm) {
		this.telefoneUm = telefoneUm;
	}

	public String getTelefoneDois() {
		return telefoneDois;
	}

	public void setTelefoneDois(String telefoneDois) {
		this.telefoneDois = telefoneDois;
	}

	public String getTelefoneTres() {
		return telefoneTres;
	}

	public void setTelefoneTres(String telefoneTres) {
		this.telefoneTres = telefoneTres;
	}

	public Long getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Long cidadeId) {
		this.cidadeId = cidadeId;
	}

}

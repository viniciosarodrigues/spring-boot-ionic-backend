package com.viniciosrodrigues.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO implements Serializable {

	private static final long serialVersionUID = 531870290754388276L;

	@NotEmpty(message = "O nome é obrigatório")
	@Email(message = "Email inválido")
	private String email;

	public EmailDTO() {
		super();
	}

	public EmailDTO(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

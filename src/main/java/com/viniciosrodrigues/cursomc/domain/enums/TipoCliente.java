package com.viniciosrodrigues.cursomc.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"), PESSOAJURIDICA(2, "Pessoa Jurídica");

	private Integer id;

	private String descricao;

	private TipoCliente(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer id) {
		if (id == null)
			return null;
		for (TipoCliente x : TipoCliente.values()) {
			if (x.id.equals(id))
				return x;

		}
		throw new IllegalArgumentException("Id do tipo Cliente inválido: " + id);
	}
}

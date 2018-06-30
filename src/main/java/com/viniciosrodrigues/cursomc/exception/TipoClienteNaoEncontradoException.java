package com.viniciosrodrigues.cursomc.exception;

public class TipoClienteNaoEncontradoException extends Exception {

	private static final long serialVersionUID = 6234072350527176435L;

	public TipoClienteNaoEncontradoException(String msg) {
		super(msg);
	}

	public TipoClienteNaoEncontradoException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

package com.viniciosrodrigues.cursomc.exception;

public class AuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 6695363618876886566L;

	public AuthorizationException(String msg) {
		super(msg);
	}

	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

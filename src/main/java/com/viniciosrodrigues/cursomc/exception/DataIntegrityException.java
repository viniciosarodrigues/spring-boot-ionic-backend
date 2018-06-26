package com.viniciosrodrigues.cursomc.exception;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 6695363618876886566L;

	public DataIntegrityException(String msg) {
		super(msg);
	}

	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

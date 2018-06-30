package com.viniciosrodrigues.cursomc.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest req) {
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrityException(DataIntegrityException e, HttpServletRequest req) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(TipoClienteNaoEncontradoException.class)
	public ResponseEntity<StandardError> tipoClienteNaoEncontradoException(TipoClienteNaoEncontradoException e,
			HttpServletRequest req) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> methodArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest req) {
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação.",
				System.currentTimeMillis());

		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			err.addError(error.getField(), error.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
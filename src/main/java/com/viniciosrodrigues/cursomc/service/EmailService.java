package com.viniciosrodrigues.cursomc.service;

import org.springframework.mail.SimpleMailMessage;

import com.viniciosrodrigues.cursomc.domain.Pedido;

public interface EmailService {
	public void sendOrderConfirmation(Pedido pedido);

	public void sendEmail(SimpleMailMessage message);
}

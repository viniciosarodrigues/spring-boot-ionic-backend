package com.viniciosrodrigues.cursomc.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.viniciosrodrigues.cursomc.domain.Pedido;

public interface EmailService {
	public void sendOrderConfirmation(Pedido pedido);

	public void sendEmail(SimpleMailMessage message);

	public void sendOrderConfirmationHtmlEmail(Pedido obj);

	public void sendHtmlEmail(MimeMessage msg);
}

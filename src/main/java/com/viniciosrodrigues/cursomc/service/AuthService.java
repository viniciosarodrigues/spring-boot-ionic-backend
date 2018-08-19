package com.viniciosrodrigues.cursomc.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.viniciosrodrigues.cursomc.domain.Cliente;
import com.viniciosrodrigues.cursomc.exception.ObjectNotFoundException;
import com.viniciosrodrigues.cursomc.repository.ClienteRepository;

@Service
public class AuthService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	BCryptPasswordEncoder bc;

	@Autowired
	EmailService emailService;

	private Random random = new Random();

	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email)
				.orElseThrow(() -> new ObjectNotFoundException("E-mail não encontrado"));

		String newPassword = newPassword();

		cliente.setSenha(bc.encode(newPassword));
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPassword);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++)
			vet[i] = randomChar();
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if (opt == 0)
			return (char) (random.nextInt(10) + 48);
		else if (opt == 0)
			return (char) (random.nextInt(26) + 65);
		else
			return (char) (random.nextInt(26) + 97);
	}

}

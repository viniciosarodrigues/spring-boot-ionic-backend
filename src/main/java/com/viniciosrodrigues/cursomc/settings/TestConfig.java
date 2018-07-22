package com.viniciosrodrigues.cursomc.settings;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.viniciosrodrigues.cursomc.service.DBService;
import com.viniciosrodrigues.cursomc.service.EmailService;
import com.viniciosrodrigues.cursomc.service.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	public DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}

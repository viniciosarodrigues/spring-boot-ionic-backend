package com.viniciosrodrigues.cursomc.settings;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.viniciosrodrigues.cursomc.service.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	public DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if (!strategy.equals("create"))
			dbService.instantiateTestDatabase();
		return true;
	}
}

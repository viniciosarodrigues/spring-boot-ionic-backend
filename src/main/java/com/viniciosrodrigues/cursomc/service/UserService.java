package com.viniciosrodrigues.cursomc.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.viniciosrodrigues.cursomc.security.UserSS;

public class UserService {

	public static UserSS autenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}

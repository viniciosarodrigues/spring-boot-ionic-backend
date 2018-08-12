package com.viniciosrodrigues.cursomc.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viniciosrodrigues.cursomc.dto.EmailDTO;
import com.viniciosrodrigues.cursomc.security.JwtUtil;
import com.viniciosrodrigues.cursomc.security.UserSS;
import com.viniciosrodrigues.cursomc.service.AuthService;
import com.viniciosrodrigues.cursomc.service.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthService authService;

	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDto) {
		authService.sendNewPassword(emailDto.getEmail());
		return ResponseEntity.noContent().build();
	}

}

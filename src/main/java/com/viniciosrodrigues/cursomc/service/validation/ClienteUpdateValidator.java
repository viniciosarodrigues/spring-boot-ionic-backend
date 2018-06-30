package com.viniciosrodrigues.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.viniciosrodrigues.cursomc.domain.Cliente;
import com.viniciosrodrigues.cursomc.dto.ClienteDTO;
import com.viniciosrodrigues.cursomc.exception.FieldMessage;
import com.viniciosrodrigues.cursomc.repository.ClienteRepository;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest req;

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public boolean isValid(ClienteDTO value, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		validaEmailCadastrado(value, list);

		adicionaListaDeErrosNoContextoDeValidacao(context, list);
		return list.isEmpty();
	}

	private void adicionaListaDeErrosNoContextoDeValidacao(ConstraintValidatorContext context,
			List<FieldMessage> list) {
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
	}

	@SuppressWarnings("unchecked")
	private void validaEmailCadastrado(ClienteDTO value, List<FieldMessage> list) {
		Map<String, String> map = (Map<String, String>) req
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long uriId = Long.parseLong(map.get("id"));

		Cliente auxiliar = clienteRepository.findByEmail(value.getEmail());

		if (auxiliar != null && !auxiliar.getId().equals(uriId))
			list.add(new FieldMessage("email", "Email já cadastrado"));
	}
}

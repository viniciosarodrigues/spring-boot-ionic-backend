package com.viniciosrodrigues.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.viniciosrodrigues.cursomc.domain.enums.TipoCliente;
import com.viniciosrodrigues.cursomc.dto.ClienteNewDTO;
import com.viniciosrodrigues.cursomc.exception.FieldMessage;
import com.viniciosrodrigues.cursomc.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (value.getTipo() == null)
			list.add(new FieldMessage("tipo", "Tipo não pode ser nulo"));
		if (value.getTipo().equals(TipoCliente.PESSOAFISICA.getId()) && !BR.isValidCPF(value.getCpfOuCnpj()))
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		if (value.getTipo().equals(TipoCliente.PESSOAJURIDICA.getId()) && !BR.isValidCNPJ(value.getCpfOuCnpj()))
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}

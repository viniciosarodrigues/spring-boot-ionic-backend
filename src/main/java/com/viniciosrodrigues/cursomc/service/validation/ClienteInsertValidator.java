package com.viniciosrodrigues.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.viniciosrodrigues.cursomc.domain.enums.TipoCliente;
import com.viniciosrodrigues.cursomc.dto.ClienteNewDTO;
import com.viniciosrodrigues.cursomc.exception.FieldMessage;
import com.viniciosrodrigues.cursomc.repository.ClienteRepository;
import com.viniciosrodrigues.cursomc.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		validaCpfOuCnpj(value, list);

		validaEmailOuCgcCadastrado(value, list);

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

	private void validaEmailOuCgcCadastrado(ClienteNewDTO value, List<FieldMessage> list) {
		if (clienteRepository.findByEmail(value.getEmail()) != null)
			list.add(new FieldMessage("email", "Email já cadastrado"));
		if (clienteRepository.findByCpfOuCnpj(value.getCpfOuCnpj()) != null)
			if (value.getTipo().equals(TipoCliente.PESSOAFISICA.getId()))
				list.add(new FieldMessage("cpfOuCnpj", "CPF já cadastrado"));
			else
				list.add(new FieldMessage("cpfOuCnpj", "CNPJ já cadastrado"));
	}

	private void validaCpfOuCnpj(ClienteNewDTO value, List<FieldMessage> list) {
		if (value.getTipo() == null)
			list.add(new FieldMessage("tipo", "Tipo não pode ser nulo"));
		if (value.getTipo().equals(TipoCliente.PESSOAFISICA.getId()) && !BR.isValidCPF(value.getCpfOuCnpj()))
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		if (value.getTipo().equals(TipoCliente.PESSOAJURIDICA.getId()) && !BR.isValidCNPJ(value.getCpfOuCnpj()))
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
	}
}

package com.viniciosrodrigues.cursomc.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.viniciosrodrigues.cursomc.domain.Cliente;
import com.viniciosrodrigues.cursomc.domain.Endereco;
import com.viniciosrodrigues.cursomc.domain.enums.Perfil;
import com.viniciosrodrigues.cursomc.domain.enums.TipoCliente;
import com.viniciosrodrigues.cursomc.dto.ClienteDTO;
import com.viniciosrodrigues.cursomc.dto.ClienteNewDTO;
import com.viniciosrodrigues.cursomc.exception.AuthorizationException;
import com.viniciosrodrigues.cursomc.exception.DataIntegrityException;
import com.viniciosrodrigues.cursomc.exception.ObjectNotFoundException;
import com.viniciosrodrigues.cursomc.repository.CidadeRepository;
import com.viniciosrodrigues.cursomc.repository.ClienteRepository;
import com.viniciosrodrigues.cursomc.repository.EnderecoRepository;
import com.viniciosrodrigues.cursomc.security.UserSS;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private S3Service s3Service;

	public List<Cliente> getListAll() {
		return clienteRepository.findAll();
	}

	public Cliente findById(Long id) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !user.getId().equals(id))
			throw new AuthorizationException("Acesso negado");

		Optional<Cliente> clienteSalvo = clienteRepository.findById(id);
		return clienteSalvo.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + Cliente.class.getName()));

	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente cliente = findById(obj.getId());
		BeanUtils.copyProperties(cliente, obj, "id", "nome", "email");
		return clienteRepository.save(obj);
	}

	public void delete(Long id) {
		try {
			clienteRepository.deleteById(findById(id).getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente com pedidos relacionados");
		}
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO obj) {
		Cliente cliente = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(),
				TipoCliente.toEnum(obj.getTipo()), pe.encode(obj.getSenha()));
		Endereco endereco = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(),
				obj.getBairro(), obj.getCep(), cliente,
				cidadeRepository.findById(obj.getCidadeId()).orElseThrow(() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id: " + obj.getCidadeId() + ", tipo: " + Cliente.class.getName())));
		cliente.getEnderecos().add(endereco);

		cliente.getTelefones().add(obj.getTelefoneUm());
		if (obj.getTelefoneDois() != null)
			cliente.getTelefones().add(obj.getTelefoneDois());
		if (obj.getTelefoneTres() != null)
			cliente.getTelefones().add(obj.getTelefoneTres());
		return cliente;
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null)
			throw new AuthorizationServiceException("Acesso negado");
		URI uri = s3Service.uploadFile(multipartFile);
		Cliente clienteAuxiliar = findById(user.getId());
		clienteAuxiliar.setUrlImagem(uri.toString());
		clienteRepository.save(clienteAuxiliar);
		return uri;
	}
}

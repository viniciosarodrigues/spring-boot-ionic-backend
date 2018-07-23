package com.viniciosrodrigues.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viniciosrodrigues.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Transactional(readOnly = true)
	public Cliente findByEmail(String email);

	@Transactional(readOnly = true)
	public Cliente findByCpfOuCnpj(String cpfOuCnpj);


}

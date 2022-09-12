package it.uniroma3.siwml.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwml.spring.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public List<User> findByNome(String nome);

	public List<User> findByNomeAndCognome(String nome, String cognome);

	public List<User> findByNomeOrCognome(String nome, String cognome);
	
	public Optional<User>  findTopByOrderByIdDesc();
}
package it.uniroma3.siwml.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwml.spring.model.Inserzione;
import it.uniroma3.siwml.spring.model.User;

public interface InserzioneRepository extends CrudRepository<Inserzione, Long> {
	
	public Optional<Inserzione> findById(Long id);

	public List<Inserzione> findByUser(User user);
	
	@Query("SELECT i FROM Inserzione i WHERE i.titolo LIKE %?1%"
			+ " OR lower(i.titolo) like lower(concat('%', ?1,'%'))"
			+ " OR lower(i.descrizione) like lower(concat('%', ?1,'%'))"
			+ " OR i.descrizione LIKE %?1%")
	public List<Inserzione> findAll(String keyword); // forse possibile anche usare 'findByTitoloOrDescrizioneIgnoreCaseContaining o simili
	
	public List<Inserzione> findAll();
}
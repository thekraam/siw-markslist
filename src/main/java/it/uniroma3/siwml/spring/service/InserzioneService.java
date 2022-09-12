package it.uniroma3.siwml.spring.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwml.spring.model.Inserzione;
import it.uniroma3.siwml.spring.model.Credentials;
import it.uniroma3.siwml.spring.model.User;
import it.uniroma3.siwml.spring.repository.InserzioneRepository;

@Service
public class InserzioneService {
	
	private final int INSERZIONI_PER_PAGINA = 12;
	
	@Autowired
	protected InserzioneRepository inserzioneRepository;

	@Transactional
	public Inserzione getById(Long id) {
		return this.inserzioneRepository.findById(id).get();
	}
	
	@Transactional
	public List<Inserzione> getAll(){
		return (List<Inserzione>) inserzioneRepository.findAll();
	}
	
    @Transactional
    public List<Inserzione> getAll(String keyword) {
    	
    	if(keyword!=null) return (List<Inserzione>) inserzioneRepository.findAll(keyword);
    	
        return (List<Inserzione>) inserzioneRepository.findAll();
    }
    
    @Transactional
    public List<Inserzione> filterByPage(List<Inserzione> inserzioniPagina, int page){
		// rimuovi non approvati
		inserzioniPagina.removeIf(i -> (!i.isApproved()));
		
		// inverti l'ordine, inserzioni create piu recentemente devono apparire prima
		Collections.reverse(inserzioniPagina);
		
		// rimuovi gli elementi oltre la pagina attuale
		inserzioniPagina.removeIf(i -> (inserzioniPagina.indexOf(i) >= (INSERZIONI_PER_PAGINA*(page))));
		
		// se non e' la prima pagina rimuovi gli elementi prima della pagina attuale
		if(page>1)
		inserzioniPagina.removeIf(i -> (inserzioniPagina.indexOf(i) < (INSERZIONI_PER_PAGINA*(page-1))));
		
		return inserzioniPagina;
    }
    
	@Transactional
	public boolean delete(Long id) {
		try {
			this.inserzioneRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
    
	@Transactional
	public Inserzione add(Inserzione inserzione) {
		return inserzioneRepository.save(inserzione);
	}
	
	@Transactional
	public List<Inserzione> getInserzioniUtente(User user){
		List<Inserzione> inserzioniUtente = inserzioneRepository.findByUser(user);
		if(inserzioniUtente.isEmpty()) return null;
		return inserzioniUtente;
	}
}

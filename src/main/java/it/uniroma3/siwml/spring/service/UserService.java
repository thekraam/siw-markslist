package it.uniroma3.siwml.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siwml.spring.model.User;
import it.uniroma3.siwml.spring.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    protected UserRepository userRepository;
    
    @Autowired
    private CredentialsService credentialsService;
    
    @Transactional
    public User getUser(Long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }

    
    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    
    @Transactional
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User user : iterable)
            result.add(user);
        return result;
    }
    
    @Transactional
    public User add(User user) {
        return userRepository.save(user);
    }
    
    @Transactional
    public List<User> userPerNomeAndCognome(String nome, String cognome) {
        return userRepository.findByNomeAndCognome(nome, cognome);
    }

    @Transactional
    public User userPerId(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent())
            return optional.get();
        else 
            return null;
    }

    @Transactional
    public boolean alreadyExists(User user) {
        List<User> artisti = this.userRepository.findByNomeAndCognome(user.getNome(), user.getCognome());
        if (artisti.size() > 0)
            return true;
        else 
            return false;
    }
    @Transactional
    public boolean deleteUsers(Long id) {
        try {
            this.userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }
    
    @Transactional
    public CredentialsService getCredentialsService() {
        return credentialsService;
    }
    
    @Transactional
	public User getUserByUsername(String username) {
		System.out.println("########### lo sfhjidghfjkdgh e " + this.credentialsService.getCredentialsByUsername(username).getUsername());
		return this.credentialsService.getCredentialsByUsername(username).getUser();
	}
}

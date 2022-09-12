package it.uniroma3.siwml.spring.service;

import static it.uniroma3.siwml.spring.model.Credentials.ADMIN_ROLE;
import static it.uniroma3.siwml.spring.model.Credentials.DEFAULT_ROLE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siwml.spring.model.Credentials;
import it.uniroma3.siwml.spring.model.User;
import it.uniroma3.siwml.spring.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
    @Autowired
    protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	@Transactional
	public Credentials getCredentials(Long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Credentials getCredentialsByUsername(String username) {
		Credentials result = this.credentialsRepository.findByUsername(username);
		return result;
	}
		
    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
    	// il primo utente e un admin
    	if(!this.credentialsRepository.findAll().iterator().hasNext()) {
    		credentials.setRole(ADMIN_ROLE);
    		credentials.getUser().setRole(ADMIN_ROLE);
    	}
    	else {
    		credentials.setRole(DEFAULT_ROLE);
    		credentials.getUser().setRole(DEFAULT_ROLE);
    	}
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
    
    @Transactional
    public String getRoleAuthenticated() {
    	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString() != "anonymousUser") {
	    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	Credentials credentials = this.getCredentialsByUsername(userDetails.getUsername());
	    	return credentials.getRole();
    	}
    	return "NO_ROLE";
    }
    
}

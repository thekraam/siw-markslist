package it.uniroma3.siwml.spring.controller.validator;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static it.uniroma3.siwml.spring.model.Inserzione.LUNGHEZZA_MASSIMA_TITOLO;
import static it.uniroma3.siwml.spring.model.Inserzione.LUNGHEZZA_MASSIMA_DESCRIZIONE;

import it.uniroma3.siwml.spring.model.Inserzione;
import it.uniroma3.siwml.spring.model.Credentials;
import it.uniroma3.siwml.spring.model.User;
import it.uniroma3.siwml.spring.service.InserzioneService;
import it.uniroma3.siwml.spring.service.CredentialsService;


@Component
public class InserzioneValidator implements Validator{
	
    @Override
    public void validate(Object o, Errors errors) {
        Inserzione inserzione = (Inserzione) o;
        String titolo = inserzione.getTitolo();
        String prezzo = inserzione.getPrezzo();
        String descrizione = inserzione.getDescrizione();
        
        
        if(descrizione.isEmpty())
        	errors.rejectValue("descrizione", "required");
        else if(descrizione.length() > LUNGHEZZA_MASSIMA_DESCRIZIONE)
        	errors.rejectValue("descrizione", "length");

        if(prezzo.isEmpty())
        	errors.rejectValue("prezzo", "required");
        for(Character c : prezzo.toCharArray())
        	if(!Character.isDigit(c)) errors.rejectValue("prezzo", "invalid");
        
        if(titolo.isEmpty())
            errors.rejectValue("nome", "required");
        else if(titolo.length() > LUNGHEZZA_MASSIMA_TITOLO)
        	errors.rejectValue("titolo", "length");
    }

	@Override
	public boolean supports(Class<?> clazz) {
		return Inserzione.class.equals(clazz);
	}

}
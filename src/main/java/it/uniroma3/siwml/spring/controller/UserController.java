package it.uniroma3.siwml.spring.controller;

import org.springframework.stereotype.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static it.uniroma3.siwml.spring.model.Credentials.ADMIN_ROLE;
import static it.uniroma3.siwml.spring.model.Credentials.DEFAULT_ROLE;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siwml.spring.controller.validator.CredentialsValidator;
import it.uniroma3.siwml.spring.controller.validator.InserzioneValidator;
import it.uniroma3.siwml.spring.controller.validator.UserValidator;
import it.uniroma3.siwml.spring.model.Credentials;
import it.uniroma3.siwml.spring.model.Inserzione;
import it.uniroma3.siwml.spring.model.User;
import it.uniroma3.siwml.spring.service.InserzioneService;
import it.uniroma3.siwml.spring.service.CredentialsService;
import it.uniroma3.siwml.spring.service.UserService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
    @Autowired
    private UserValidator userValidator;

    @Autowired
	private CredentialsValidator credentialsValidator;
    
    @Autowired
    private InserzioneService inserzioneService;
    
    @Autowired
	private CredentialsService credentialsService;
    
	
	@RequestMapping(value = {"/admin/manageusers"}, method = RequestMethod.GET)
	public String getUsers(Model model) {
		
		model.addAttribute("users", userService.getAllUsers());

		return "admin/manageusers";
	}
	
	@RequestMapping(value = {"/admin/manageusers/ads/{id}"}, method = RequestMethod.GET)
	public String getUserAds(@PathVariable("id") Long id, Model model) {
		
		User utente = userService.getUser(id);
		List<Inserzione> inserzioniUtente = inserzioneService.getInserzioniUtente(utente);
		
		model.addAttribute("inserzioni", inserzioniUtente);
		model.addAttribute("user", utente);
		
		return "admin/userads";
	}
	

}

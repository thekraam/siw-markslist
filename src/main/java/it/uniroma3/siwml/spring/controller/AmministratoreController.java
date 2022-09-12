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
public class AmministratoreController {

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
    

	@RequestMapping(value = {"/admin/new"}, method = RequestMethod.GET)
	public String addAdmin(Model model) {
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("user", new User());
		return "admin/new";
	}
	
	@RequestMapping(value = {"/admin/new"}, method = RequestMethod.POST)
	public String addAdminForm(@ModelAttribute("user") User user,
            BindingResult userBindingResult,
            @ModelAttribute("credentials") Credentials credentials,
            BindingResult credentialsBindingResult,
            Model model) {

        // valida lo user e le credenziali
        this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // se non ci sono errori salva tutto e porta l'utente alla pagina di approvazione
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
        	
            credentials.setUser(user);
            
            Credentials credenzialiAdmin = credentialsService.saveCredentials(credentials);
            credenzialiAdmin.setRole(ADMIN_ROLE);
            
            return "admin/admincreated";
        }
		
		return "admin/panel";
	}
	
	   // /admin/manageusers/ads/

	@RequestMapping(value = {"/admin/panel"}, method = RequestMethod.GET)
	public String getPanel(Model model) {
		return "admin/panel";
	}
		
}
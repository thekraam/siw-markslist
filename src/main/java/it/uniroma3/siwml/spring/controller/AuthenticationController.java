package it.uniroma3.siwml.spring.controller;

import static it.uniroma3.siwml.spring.model.Credentials.ADMIN_ROLE;
import static it.uniroma3.siwml.spring.model.Credentials.DEFAULT_ROLE;

import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.uniroma3.siwml.spring.controller.validator.CredentialsValidator;
import it.uniroma3.siwml.spring.controller.validator.UserValidator;
import it.uniroma3.siwml.spring.model.Inserzione;
import it.uniroma3.siwml.spring.model.Credentials;
import it.uniroma3.siwml.spring.model.User;
import it.uniroma3.siwml.spring.repository.UserRepository;
import it.uniroma3.siwml.spring.service.CredentialsService;
import it.uniroma3.siwml.spring.service.UserService;

@Controller
@SessionAttributes(value="role", types= {String.class})
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@RequestMapping("role")
	public String roleUser() {
		String role=credentialsService.getRoleAuthenticated();
    	return role;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm (Model model) {
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		
		return "register";
	}
	
    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user,
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
            
            credentialsService.saveCredentials(credentials);
            
            return "registered";
        }
        // altrimenti stampa gli errori in console...
        //if(userBindingResult.hasErrors()) {
        //	System.out.println("######## Errore ######## [ "+ userBindingResult.getAllErrors() + " ]");
        //}
        return "register";
    }
	
}

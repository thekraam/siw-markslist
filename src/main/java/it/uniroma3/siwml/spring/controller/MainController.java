package it.uniroma3.siwml.spring.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siwml.spring.model.Inserzione;
import it.uniroma3.siwml.spring.repository.InserzioneRepository;
import it.uniroma3.siwml.spring.service.CredentialsService;
import it.uniroma3.siwml.spring.service.InserzioneService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//import it.uniroma3.siwcdf.spring.service.CredentialsService;


@Controller
@ComponentScan(basePackages = "it.uniroma3.siwcdf.spring.controller")
public class MainController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private InserzioneService inserzioneService;
	
	
	//@RequestMapping(value = {"error"}, method = RequestMethod.GET)
	//public String errorPage(Model model) {
	//	model.addAttribute("role", credentialsService.getRoleAuthenticated());
	//	return "error";
	//}
	
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
		
		return "redirect:/index/page/1";
	}
	
	@RequestMapping(value = {"/ad/{id}"}, method = RequestMethod.GET)
	public String getInserzione(@PathVariable("id") Long id, Model model) {
		model.addAttribute("role", credentialsService.getRoleAuthenticated());
		model.addAttribute("inserzione", inserzioneService.getById(id));
		
		return "ad";
	}
	
	@RequestMapping(value = {"/admin/listtest"}, method = RequestMethod.GET)
	public String listtest(Model model) {

		
		int k = inserzioneService.getAll().size();
		for(int i = k; i<k+13; i++) {
			Inserzione inserzione = new Inserzione();
			inserzione.setApproved(true);
			inserzione.setContatto("1");
			inserzione.setData(Date.valueOf(LocalDate.now()));
			inserzione.setDescrizione("Prova descrizione");
			inserzione.setTitolo("ELEMENTO n." + i);
			inserzione.setImagePath("prova.png");
			inserzione.setPrezzo(Integer.toString(300+i));
			inserzione.setUser(credentialsService.getCredentialsByUsername("lol1").getUser());
			
			inserzioneService.add(inserzione);
		}
		
		return "redirect:/index";
	}
	
	@RequestMapping(value = {"/index/page/{page}"}, method = RequestMethod.GET)
	public String getPagina(@PathVariable("page") int page, Model model) {
		
		List<Inserzione> inserzioniPagina = inserzioneService.getAll();
		
		boolean hasProssimaPagina = false;
		boolean hasPrecedentePagina = (page > 1) ? true : false;
		
		inserzioniPagina.removeIf(i -> !i.isApproved());
		
		if(inserzioniPagina.size() > 12*page) hasProssimaPagina = true;
		
		inserzioniPagina = inserzioneService.filterByPage(inserzioniPagina, page);
		
		

		model.addAttribute("inserzioniPagina", inserzioniPagina);
		model.addAttribute("hasProssimaPagina", hasProssimaPagina);
		model.addAttribute("hasPrecedentePagina", hasPrecedentePagina);
		model.addAttribute("role", credentialsService.getRoleAuthenticated());
		
		return "index";
	}
	
	@RequestMapping(value = {"/index/search/result"}, method = RequestMethod.POST)
	public String searchInserzione(@RequestParam("keyword") String keyword, Model model) {
		
		if(keyword.isEmpty()) {
			List<Inserzione> inserzioniVuote = new ArrayList<>();
			model.addAttribute("inserzioniPagina", inserzioniVuote);
			return "redirect:/index/page/1";
		}
		
		return "redirect:/index/search/" + keyword;
	}
	
	@RequestMapping(value = {"/index/search/{keyword}"}, method = RequestMethod.GET)
	public String searchInserzioneForced(@PathVariable("keyword") String keyword, Model model) {

		int page = 1; // la nostra pagina per i primi risultati e uno
		List<Inserzione> inserzioniTrovate = inserzioneService.getAll(keyword);
		inserzioniTrovate = inserzioneService.filterByPage(inserzioniTrovate, page);
		
		model.addAttribute("inserzioniPagina", inserzioniTrovate);
		model.addAttribute("role", credentialsService.getRoleAuthenticated());
		
		return "result";
	}
	
	//@RequestMapping(value = {"/aboutus"}, method = RequestMethod.GET)
	//public String chiSiamo(Model model) {
	//	return "aboutus";
	//}
	
	//@RequestMapping(value = {"/contacts"}, method = RequestMethod.GET)
	//public String contattaci(Model model) {	
	//	return "contacts";
	//}
}
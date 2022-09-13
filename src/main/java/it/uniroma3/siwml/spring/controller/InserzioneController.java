package it.uniroma3.siwml.spring.controller;

import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siwml.upload.FileUploadUtil;
import it.uniroma3.siwml.spring.controller.validator.InserzioneValidator;
import it.uniroma3.siwml.spring.model.Inserzione;
import it.uniroma3.siwml.spring.model.User;
import it.uniroma3.siwml.spring.service.InserzioneService;
import it.uniroma3.siwml.spring.service.CredentialsService;
import it.uniroma3.siwml.spring.service.UserService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class InserzioneController {

	@Autowired
	private InserzioneService inserzioneService;
	
	@Autowired
	private InserzioneValidator inserzioneValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	
	@RequestMapping(value = {"/myads"}, method = RequestMethod.GET)
	public String showMieInserzioni(Model model) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User utenteCorrente = credentialsService.getCredentialsByUsername(userDetails.getUsername()).getUser();
		
		List<Inserzione> inserzioniUtente = inserzioneService.getInserzioniUtente(utenteCorrente);
		
		model.addAttribute("inserzioni", inserzioniUtente);
		model.addAttribute("user", utenteCorrente);
		
		return "myads";
	}
	
	@RequestMapping(value = {"/admin/manageads"}, method = RequestMethod.GET)
	public String manageInserzioni(Model model) {
		
		List<Inserzione> inserzioniAttive = inserzioneService.getAll();
		List<Inserzione> inserzioniInattive = new ArrayList<>();
		inserzioniInattive.addAll(inserzioniAttive);
		inserzioniAttive.removeIf(i -> (!i.isApproved()));
		inserzioniInattive.removeIf(i -> (i.isApproved()));
		
		model.addAttribute("inserzioniAttive", inserzioniAttive);
		model.addAttribute("inserzioniInattive", inserzioniInattive);
		
		return "admin/manageads";
	}
	
	
	@RequestMapping(value = {"/admin/manageads/delete/{id}"}, method = RequestMethod.GET)
	public String deleteInserzione(@PathVariable("id") Long id, Model model) {
		
		if(inserzioneService.delete(id)) return "/admin/operationsuccessful";
		
		return "admin/manageads";
	}
	
	@RequestMapping(value = {"/admin/manageads/approve/{id}"}, method = RequestMethod.GET)
	public String approveInserzione(@PathVariable("id") Long id, Model model) {
		
		Inserzione inserzione = inserzioneService.getById(id);
		inserzione.setApproved(true);
		inserzioneService.add(inserzione);
		
		return "admin/operationsuccessful";
	}
	
	@RequestMapping(value = {"/myads/delete/{id}"}, method = RequestMethod.GET)
	public String deleteMiaInserzione(@PathVariable("id") Long id, Model model) {
		
		inserzioneService.delete(id);
		
		return "deletesuccessful";
	}
	
	@RequestMapping(value = {"/add"}, method = RequestMethod.GET)
	public String addInserzione(Model model) {
		model.addAttribute("inserzione", new Inserzione());
		return "add";
	}
	
	@RequestMapping(value = {"/add"}, method = RequestMethod.POST)
	public String addInserzioneForm(@ModelAttribute("inserzione") Inserzione inserzione, @RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult, Model model) throws IOException {
		
		inserzioneValidator.validate(inserzione, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			String imgName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			inserzione.setImagePath(imgName);
			
			inserzione.setData(Date.valueOf(LocalDate.now()));
		
			
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User utenteCorrente = credentialsService.getCredentialsByUsername(userDetails.getUsername()).getUser();
			
			inserzione.setUser(utenteCorrente);
			
			Inserzione inserzioneCorrente = inserzioneService.add(inserzione);
			
			utenteCorrente.getInserzioni().add(inserzioneCorrente);
			userService.saveUser(utenteCorrente);
			
			String directoryUpload = "immagini-inserzioni/" + "user-" + utenteCorrente.getId() + "/" + "inserzione-" + inserzioneCorrente.getId();
			FileUploadUtil.saveFile(directoryUpload, imgName, multipartFile);
			
			return "added";
		}
		
		return "add";
	}
	
}

package it.uniroma3.siwml.spring.model;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Data;

@Entity
public @Data class Inserzione {
	
	public static final int LUNGHEZZA_MASSIMA_DESCRIZIONE = 100;
	public static final int LUNGHEZZA_MASSIMA_TITOLO = 20;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String titolo;
	
	@Column(nullable=false)
	private String prezzo;
	
	@Column(nullable=false)
	private Date data;
	
	@Column(nullable=false)
	private String descrizione;
	
	@Column(nullable=false)
	private String contatto;
	
	@Column(nullable=true)
	private boolean approved;
	
	@Column(nullable = false, length = 64)
    private String imagePath;
	
	@ManyToOne
	private User user;
}

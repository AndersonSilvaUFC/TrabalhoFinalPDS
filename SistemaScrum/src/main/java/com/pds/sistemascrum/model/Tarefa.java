package com.pds.sistemascrum.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Tarefa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank
	private String titulo;
	
	@NotBlank
	private String status;
	
	@ManyToOne()
	private Integrante integrante;

	@ManyToOne
	private Sprint sprint;
	
	public Tarefa() {}

	public Tarefa(Integer id, @NotBlank String titulo, @NotBlank String status, Integrante integrante) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.status = status;
		this.integrante = integrante;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integrante getIntegrante() {
		return integrante;
	}

	public void setIntegrante(Integrante integrante) {
		this.integrante = integrante;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
}

package com.pds.sistemascrum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Integrante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;	
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String cargo;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "integrante_id")
	private List<Tarefa> tarefas;
	
	public Integrante() {}

	public Integrante(Integer id, @NotBlank String nome, @NotBlank String cargo, List<Tarefa> tarefas) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.tarefas = tarefas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
	
}

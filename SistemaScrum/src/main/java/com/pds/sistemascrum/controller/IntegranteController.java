package com.pds.sistemascrum.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pds.sistemascrum.model.Integrante;
import com.pds.sistemascrum.repository.IntegranteRepository;

@RestController
@RequestMapping("/api/integrantes")
public class IntegranteController {

	@Autowired
	public IntegranteRepository integranteReposiroty;
	
	@PostMapping("")
	public Integrante cadastrarIntegrante(@RequestBody Integrante integrante) {
		return integranteReposiroty.save(integrante);
	}
	
	@GetMapping("")
	public List<Integrante> listarIntegrantes(){
		return integranteReposiroty.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Integrante> recuperarIntegrante(@PathVariable Integer id) {
		return integranteReposiroty.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public Integer deletarInteegrante(@PathVariable Integer id) {
		integranteReposiroty.deleteById(id);
		return id;
	}
	
	@PutMapping("/{id}")
	public Integrante atualizarIntegrante(@PathVariable Integer id, @RequestBody Integrante integrante) {
		if(integranteReposiroty.existsById(id)) {
			integrante.setId(id);
			return integranteReposiroty.save(integrante);
		} else
			return null;
	}
}

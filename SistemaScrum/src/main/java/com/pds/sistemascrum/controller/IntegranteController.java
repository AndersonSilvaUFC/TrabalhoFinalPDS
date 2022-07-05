package com.pds.sistemascrum.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/integrantes")
public class IntegranteController {

	@Autowired
	public IntegranteRepository integranteReposiroty;
	
	@PostMapping("")
	public ResponseEntity<Integrante> cadastrarIntegrante(@Valid @RequestBody Integrante integrante) {
		return new ResponseEntity<>(integranteReposiroty.save(integrante), HttpStatus.OK);
	}
	
	@GetMapping("")
	public List<Integrante> listarIntegrantes(){
		return integranteReposiroty.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Integrante> recuperarIntegrante(@PathVariable Integer id) {
		if(integranteReposiroty.existsById(id))
			return new ResponseEntity<>(integranteReposiroty.findById(id).get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deletarIntegrante(@PathVariable Integer id) {
		if(integranteReposiroty.existsById(id)) {
			integranteReposiroty.deleteById(id);
			return new ResponseEntity<>(id,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Integrante> atualizarIntegrante(@PathVariable Integer id, @Valid @RequestBody Integrante integrante) {
		if(integranteReposiroty.existsById(id)) {
			integrante.setId(id);
			return new ResponseEntity<>(integranteReposiroty.save(integrante), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

package com.pds.sistemascrum.controller;

import java.util.Date;
import java.util.List;

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
import com.pds.sistemascrum.model.Sprint;
import com.pds.sistemascrum.repository.SprintRepository;


@RestController
@RequestMapping("/api/sprints")
public class SprintController {

	@Autowired
	public SprintRepository sprintRepository;
	
	@PostMapping("")
	public ResponseEntity<Sprint> cadastrarSprint(@RequestBody Sprint sprint) {
		if(sprint.getDataInicial().before(sprint.getDataFinal())) {
			sprintRepository.save(sprint);
			return new ResponseEntity<>(sprint, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("")
	public List<Sprint> listarSprints(){
		return sprintRepository.findAll();
	}
	
	@GetMapping("/sprint/{id}")
	public ResponseEntity<Sprint> buscarSprintPorId(Integer id) {
		if(!(sprintRepository.existsById(id))) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(sprintRepository.getById(id), HttpStatus.OK);
		}
	}
		
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deletarSprint(@PathVariable Integer id) {	
		if(sprintRepository.existsById(id)) {
			sprintRepository.deleteById(id);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Sprint> atualizarSprint(@PathVariable Integer id, @RequestBody Sprint sprint) {
		if(sprintRepository.existsById(id)) {
			sprint.setId(id);
			sprintRepository.save(sprint);
			return new ResponseEntity<>(sprintRepository.findById(id).get(), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

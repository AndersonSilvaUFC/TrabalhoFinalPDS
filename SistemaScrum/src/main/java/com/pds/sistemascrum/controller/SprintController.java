package com.pds.sistemascrum.controller;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Sprint criada, retorna a sprint criada"),
			@ApiResponse(code = 400, message = "Erro, data inicial mais recente que a final")
	})
	@ApiOperation(value = "Cria sprint")
	@PostMapping("")
	public ResponseEntity<Sprint> cadastrarSprint(@RequestBody Sprint sprint) {
		if(sprint.getDataInicial().before(sprint.getDataFinal())) {
			sprintRepository.save(sprint);
			return new ResponseEntity<>(sprint, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna todas as sprints"),
	})
	@ApiOperation(value = "Lista sprints")
	@GetMapping("")
	public List<Sprint> listarSprints(){
		return sprintRepository.findAll();
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sprint encontrada, retorna a sprint encontrada"),
			@ApiResponse(code = 404, message = "Sprint não encontrada")
	})
	@ApiOperation(value = "Recupera sprint pelo ID informado")
	@GetMapping("/{id}")
	public ResponseEntity<Sprint> buscarSprintPorId(Integer id) {
		if(!(sprintRepository.existsById(id))) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(sprintRepository.findById(id).get(), HttpStatus.OK);
		}
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sprint excluida, retorna o ID sprint excluida"),
			@ApiResponse(code = 400, message = "Sprint não encontrada para ser excluida")
	})
	@ApiOperation(value = "Exclui uma sprint pelo ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deletarSprint(@PathVariable Integer id) {	
		if(sprintRepository.existsById(id)) {
			sprintRepository.deleteById(id);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sprint atualizada, retorna a sprint atualizada"),
			@ApiResponse(code = 404, message = "Não encontrada a sprint para ser atualizada")
	})
	@ApiOperation(value = "Atualiza a sprint")
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

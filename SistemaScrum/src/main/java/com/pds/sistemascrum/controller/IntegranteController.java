package com.pds.sistemascrum.controller;

import java.util.List;
import java.util.Optional;

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
import com.pds.sistemascrum.repository.IntegranteRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/integrantes")
public class IntegranteController {

	@Autowired
	public IntegranteRepository integranteReposiroty;

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Integrante criado com sucesso, retorna o Integrante criado")
	})
	@ApiOperation(value = "Cria integrante")
	@PostMapping("")
	public ResponseEntity<Integrante> cadastrarIntegrante(@Valid @RequestBody Integrante integrante) {
		return new ResponseEntity<>(integranteReposiroty.save(integrante), HttpStatus.CREATED);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a lista de integrantes")
	})
	@ApiOperation(value = "Lista integrantes")
	@GetMapping("")
	public List<Integrante> listarIntegrantes(){
		return integranteReposiroty.findAll();
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Integrante encontrado, retorna o Integrante encontrado"),
			@ApiResponse(code = 404, message = "Integrante não encontrado")
	})
	@ApiOperation(value = "Recupera integrante pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<Integrante> recuperarIntegrante(@PathVariable Integer id) {
		if(integranteReposiroty.existsById(id))
			return new ResponseEntity<>(integranteReposiroty.findById(id).get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Integrante excluído, retorna o ID do integrante excluído"),
			@ApiResponse(code = 400, message = "Não existe integrante com o ID informado")
	})
	@ApiOperation(value = "Exclui um integrante pelo id")
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deletarIntegrante(@PathVariable Integer id) {
		if(integranteReposiroty.existsById(id)) {
			integranteReposiroty.deleteById(id);
			return new ResponseEntity<>(id,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Integrante atualizado, retorna o Integrante atualizado"),
			@ApiResponse(code = 404, message = "Nao encontrado o Integrante para ser atualizado")
	})
	@ApiOperation(value = "Atualiza um integrante")
	@PutMapping("/{id}")
	public ResponseEntity<Integrante> atualizarIntegrante(@PathVariable Integer id, @Valid @RequestBody Integrante integrante) {
		if(integranteReposiroty.existsById(id)) {
			integrante.setId(id);
			return new ResponseEntity<>(integranteReposiroty.save(integrante), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

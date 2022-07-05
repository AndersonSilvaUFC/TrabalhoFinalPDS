package com.pds.sistemascrum.controller;

import com.pds.sistemascrum.model.Integrante;
import com.pds.sistemascrum.model.Tarefa;
import com.pds.sistemascrum.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private IntegranteController integranteController;

    @GetMapping("")
    public List<Tarefa> listarTarefas(){
        return tarefaRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> recuperarTarefa(@PathVariable Integer id){
        if(tarefaRepository.existsById(id)){
            return new ResponseEntity<>(tarefaRepository.findById(id).get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/status/{status}")
    public List<Tarefa> listarTarefasPorStatus(@PathVariable String status){
        return tarefaRepository.findByStatus(status);
    }

    @GetMapping("/integrante/{id}")
    public ResponseEntity<List<Tarefa>> listarTarefasPorIntegrante(@PathVariable Integer id){
        if(integranteController.recuperarIntegrante(id).getStatusCodeValue() == 200 ){
            return new ResponseEntity<>(tarefaRepository.findByIntegranteId(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("")
    public ResponseEntity<Tarefa> cadastrarTarefa(@Valid @RequestBody Tarefa tarefa) {
        ResponseEntity<Integrante> resposta = integranteController.recuperarIntegrante(tarefa.getIntegrante().getId());

        if (resposta.getStatusCodeValue() == 200) {
            tarefa.setIntegrante(resposta.getBody());
            Tarefa t = tarefaRepository.save(tarefa);

            return new ResponseEntity<>(t, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Integer id, @Valid @RequestBody Tarefa tarefa){
        Integrante integranteTarefa = tarefa.getIntegrante();
        if(tarefaRepository.existsById(id)) {
            tarefa.setId(id);
            if(integranteTarefa != null) {
                if (integranteController.recuperarIntegrante(integranteTarefa.getId()).getStatusCodeValue() == 200)
                    return new ResponseEntity<>(tarefaRepository.save(tarefa), HttpStatus.OK);
                else
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(tarefaRepository.save(tarefa), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deletarTarefa(@PathVariable Integer id){
        if(tarefaRepository.existsById(id)){
            tarefaRepository.deleteById(id);
            return new ResponseEntity<>( id , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

package com.pds.sistemascrum.controller;

import com.pds.sistemascrum.model.Integrante;
import com.pds.sistemascrum.model.Sprint;
import com.pds.sistemascrum.model.Tarefa;
import com.pds.sistemascrum.repository.TarefaRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @Autowired
    private SprintController sprintController;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna todas as tarefas"),
    })
    @ApiOperation(value = "Lista as tarefas")
    @GetMapping("")
    public List<Tarefa> listarTarefas(){
        return tarefaRepository.findAll();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tarefa encontrada, retorna a tarefa encontrada"),
            @ApiResponse(code = 404, message = "Tarefa não encontrda")
    })
    @ApiOperation(value = "Recupera uma tarefa pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> recuperarTarefa(@PathVariable Integer id){
        if(tarefaRepository.existsById(id)){
            return new ResponseEntity<>(tarefaRepository.findById(id).get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna tarefas filtradas pelo status informado"),
    })
    @ApiOperation(value = "Lista tarefas por status")
    @GetMapping("/status/{status}")
    public List<Tarefa> listarTarefasPorStatus(@PathVariable String status){
        return tarefaRepository.findByStatus(status);
    }
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna tarefas do integrante informado"),
            @ApiResponse(code = 400, message = "Erro, integrante não existe")
    })
    @ApiOperation(value = "Lista tarefas filtradas pelo integrante informado")
    @GetMapping("/integrante/{id}")
    public ResponseEntity<List<Tarefa>> listarTarefasPorIntegrante(@PathVariable Integer id){
        if(integranteController.recuperarIntegrante(id).getStatusCodeValue() == 200 ){
            return new ResponseEntity<>(tarefaRepository.findByIntegranteId(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tarefa criada, retorna a tarefa criada"),
            @ApiResponse(code = 400, message = "Integrante reponsável pela tarefa ou Sprint não existem")
    })
    @ApiOperation(value = "Cria uma tarefa")
    @PostMapping("")
    public ResponseEntity<Tarefa> cadastrarTarefa(@Valid @RequestBody Tarefa tarefa) {
        ResponseEntity<Integrante> respostaIntegrante = integranteController.recuperarIntegrante(tarefa.getIntegrante().getId());
        ResponseEntity<Sprint> respostaSprint = sprintController.buscarSprintPorId(tarefa.getIntegrante().getId());

        if (respostaIntegrante.getStatusCodeValue() == 200) {
            tarefa.setIntegrante(respostaIntegrante.getBody());

            if(respostaSprint.getStatusCodeValue() == 200){
                tarefa.setSprint(respostaSprint.getBody());

                return new ResponseEntity<>(tarefaRepository.save(tarefa), HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tarefa atualizada, retorna a tarefa atualizada"),
            @ApiResponse(code = 400, message = "Integrante responsável pela tarefa o sprint não existem"),
            @ApiResponse(code = 404, message = "Tarefa não encontrada para ser atualizada")
    })
    @ApiOperation(value = "Atualiza uma tarefa")
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Integer id, @Valid @RequestBody Tarefa tarefa){
        Integrante integranteTarefa = tarefa.getIntegrante();
        Sprint sprintTarefa = tarefa.getSprint();
        if(tarefaRepository.existsById(id)) {
            tarefa.setId(id);
            if(integranteTarefa != null) {
                if (integranteController.recuperarIntegrante(integranteTarefa.getId()).getStatusCodeValue() == 200) {
                    if (sprintTarefa != null) {
                        if (sprintController.buscarSprintPorId(sprintTarefa.getId()).getStatusCodeValue() == 200)
                            return new ResponseEntity<>(tarefaRepository.save(tarefa), HttpStatus.OK);
                        else
                            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }else{
                        return new ResponseEntity<>(tarefaRepository.save(tarefa), HttpStatus.OK);
                    }
                }else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }else {
                return new ResponseEntity<>(tarefaRepository.save(tarefa), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tarefa excluída, retorna o ID da tarefa excluída"),
            @ApiResponse(code = 400, message = "Tarefa não eocntrada para ser excluída")
    })
    @ApiOperation(value = "Exlui uma tarefa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deletarTarefa(@PathVariable Integer id){
        if(tarefaRepository.existsById(id)){
            tarefaRepository.deleteById(id);
            return new ResponseEntity<>( id , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

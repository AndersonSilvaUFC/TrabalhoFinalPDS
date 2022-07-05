package com.pds.sistemascrum.repository;

import com.pds.sistemascrum.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

    @Query(value = "SELECT * FROM Tarefa WHERE status = :status", nativeQuery = true)
    public List<Tarefa> findByStatus(String status);

    @Query(value = "SELECT * FROM  Tarefa  WHERE integrante_id = :id", nativeQuery = true)
    public List<Tarefa> findByIntegranteId(Integer id);
}

package com.pds.sistemascrum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pds.sistemascrum.model.Integrante;

@Repository
public interface IntegranteRepository extends JpaRepository<Integrante, Integer> {

}

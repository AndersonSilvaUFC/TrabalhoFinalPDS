package com.pds.sistemascrum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.pds.sistemascrum.model.Sprint;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer>{

}

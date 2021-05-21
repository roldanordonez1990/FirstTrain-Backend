package com.firsttrain_backend.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.firsttrain_backend.model.entities.NivelEntrenamiento;

@Repository
public interface NivelRepository extends CrudRepository<NivelEntrenamiento, Integer>{
	
	@Query(value="SELECT * FROM nivel_entrenamiento", nativeQuery = true)
	public List<NivelEntrenamiento>getTodosLosNiveles();

}

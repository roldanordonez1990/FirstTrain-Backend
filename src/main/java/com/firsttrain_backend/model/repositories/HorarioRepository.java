package com.firsttrain_backend.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.firsttrain_backend.model.entities.Horario;
import com.firsttrain_backend.model.entities.Reserva;

@Repository
public interface HorarioRepository extends CrudRepository<Horario, Integer>{
	
	@Query(value="SELECT horas FROM horario", nativeQuery = true)
	public List<Horario>getHorasDelHorario();
	
	@Query(value="SELECT * FROM horario WHERE disponible = 1", nativeQuery = true)
	public List<Horario>getHorasDelHorarioDisponibles();

}

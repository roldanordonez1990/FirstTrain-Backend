package com.firsttrain_backend.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.firsttrain_backend.controller.DTO;
import com.firsttrain_backend.model.entities.Horario;
import com.firsttrain_backend.model.entities.Reserva;
import com.firsttrain_backend.model.entities.Usuario;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Integer>{
	
	
	@Query(value="SELECT SUM(plazas) FROM reserva WHERE id_hora = ?", nativeQuery = true)
	public long getSumaPlazas(int id_hora);
	
	
	@Query(value="SELECT * FROM reserva WHERE id_hora = ? and id_usu = ?", nativeQuery = true)
	public Reserva getComprobarReserva(int id_hora, int id_usu);
	
	@Query(value="SELECT nombre, apellidos, fecha, horas, id_reservas FROM usuario as u, reserva as re, horario as ho "
			+ "WHERE re.id_hora = ho.id_horario and re.id_usu = u.id_usuario and re.id_usu = ?", nativeQuery = true)
	public List<DTO> getMisReservas(int id_usu);
	

}

package com.firsttrain_backend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firsttrain_backend.jwtSecurity.AutenticadorJWT;
import com.firsttrain_backend.model.entities.Horario;
import com.firsttrain_backend.model.entities.NivelEntrenamiento;
import com.firsttrain_backend.model.repositories.HorarioRepository;
import com.firsttrain_backend.model.repositories.NivelRepository;

@CrossOrigin
@RestController
public class HorarioController {
	
	@Autowired
	//UsuarioRepository usuRep;
	HorarioRepository horaRep;
	
	@GetMapping("todasLasHoras/all")
	public DTO todosLasHoras(HttpServletRequest request) {

		DTO dto = new DTO();
		dto.put("result", "fail");
		try {
			// Obtengo el id del usuario a través del token del request del cliente
			//int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Listado de cometidos (entidad) gracias al método del repositorio
			List<Horario> horas = (List<Horario>) this.horaRep.findAll();
			// Listado de DTO de cometidos que se va a enviar al cliente
			List<DTO> horasDTO = new ArrayList<DTO>();
			// Recorremos la lista de entidad cometidos y se la añadimos a la lista DTO
			// cometidos
			for (Horario e : horas) {
				horasDTO.add(getHoras(e));
			}
			dto.put("horas", horasDTO);
			dto.put("result", "ok");
		} catch (Exception e) {

		}

		return dto;
	}
	
	/**
	 * 
	 */
	
	@GetMapping("todasLasHorasDisponibles/all")
	public DTO getHorasDelHorarioDisponibles(HttpServletRequest request) {

		DTO dto = new DTO();
		dto.put("result", "fail");
		try {
			// Obtengo el id del usuario a través del token del request del cliente
			//int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Listado de cometidos (entidad) gracias al método del repositorio
			List<Horario> horas = (List<Horario>) this.horaRep.getHorasDelHorarioDisponibles();
			// Listado de DTO de cometidos que se va a enviar al cliente
			List<DTO> horasDTO = new ArrayList<DTO>();
			// Recorremos la lista de entidad cometidos y se la añadimos a la lista DTO
			// cometidos
			for (Horario e : horas) {
				horasDTO.add(getHoras(e));
			}
			dto.put("horasDisponibles", horasDTO);
			dto.put("result", "ok");
		} catch (Exception e) {

		}

		return dto;
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	private DTO getHoras(Horario e) {
		DTO dto = new DTO();
		dto.put("id_horario", e.getIdHorario());
		dto.put("horas", e.getHoras());
		//dto.put("id_actividad", e.getActividad());
		dto.put("disponible", e.getDisponible());

		return dto;

	}

}

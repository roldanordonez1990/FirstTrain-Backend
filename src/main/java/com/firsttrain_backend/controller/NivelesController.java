package com.firsttrain_backend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firsttrain_backend.jwtSecurity.AutenticadorJWT;
import com.firsttrain_backend.model.entities.NivelEntrenamiento;
import com.firsttrain_backend.model.repositories.NivelRepository;
import com.firsttrain_backend.model.repositories.UsuarioRepository;

@CrossOrigin
@RestController
public class NivelesController {

	@Autowired
	//UsuarioRepository usuRep;
	NivelRepository nivelRep;
	
	
	//Buscar todos los usuarios 
		@GetMapping("niveles/all")
		public Iterable<NivelEntrenamiento> getTodosLosNiveles() {

			return this.nivelRep.findAll();
		}
	
	@GetMapping("todosLosNiveles/all")
	public DTO todosLosNiveles(HttpServletRequest request) {

		DTO dto = new DTO();
		dto.put("result", "fail");
		try {
			// Obtengo el id del usuario a través del token del request del cliente
			//int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Listado de cometidos (entidad) gracias al método del repositorio
			List<NivelEntrenamiento> niveles = (List<NivelEntrenamiento>) this.nivelRep.findAll();
			// Listado de DTO de cometidos que se va a enviar al cliente
			List<DTO> nivelesDTO = new ArrayList<DTO>();
			// Recorremos la lista de entidad cometidos y se la añadimos a la lista DTO
			// cometidos
			for (NivelEntrenamiento e : niveles) {
				nivelesDTO.add(getNombreNiveles(e));
			}
			dto.put("niveles", nivelesDTO);
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
	private DTO getNombreNiveles(NivelEntrenamiento e) {
		DTO dto = new DTO();
		dto.put("idniveles", e.getIdNivelEntrenamiento());
		dto.put("nivel_corto", e.getNivelCorto());
		dto.put("descripcion_nivel", e.getDescripcionNivel());
		return dto;

	}

}

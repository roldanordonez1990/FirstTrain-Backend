package com.firsttrain_backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.firsttrain_backend.jwtSecurity.AutenticadorJWT;
import com.firsttrain_backend.model.entities.Horario;
import com.firsttrain_backend.model.entities.Reserva;
import com.firsttrain_backend.model.entities.Usuario;
import com.firsttrain_backend.model.repositories.HorarioRepository;
import com.firsttrain_backend.model.repositories.ReservaRepository;
import com.firsttrain_backend.model.repositories.UsuarioRepository;


@CrossOrigin
@RestController
public class ReservaController {

	@Autowired
	ReservaRepository reservaRep;
	@Autowired
	HorarioRepository horaRep;
	@Autowired
	UsuarioRepository usuRep;

	@GetMapping("pruebaInnerJoin/all")
	public DTO pruebaInnerJoin(HttpServletRequest request) {

		DTO dto = new DTO();
		dto.put("result", "fail");
		try {
			// Obtengo el id del usuario a través del token del request del cliente
			// int idUsuAutenticado =
			// AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Listado de cometidos (entidad) gracias al método del repositorio
			// List<Horario> horas = (List<Horario>) this.horaRep.findAll();

			List<Reserva> reservas = (List<Reserva>) this.reservaRep.findAll();

			int plazas = 0;

			// Listado de DTO de cometidos que se va a enviar al cliente
			// List<DTO> horasDTO = new ArrayList<DTO>();

			List<DTO> reservasDTO = new ArrayList<DTO>();
			List<DTO> plazasDTO = new ArrayList<DTO>();
			// Recorremos la lista de entidad cometidos y se la añadimos a la lista DTO
			// cometidos

			for (Reserva e : reservas) {
				reservasDTO.add(getDatosReserva(e));

			}

			// dto.put("horas", horasDTO);
			dto.put("reservas", reservasDTO);
			// dto.put("plazas", plazas);
			dto.put("result", "ok");
		} catch (Exception e) {

		}

		return dto;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */

	@GetMapping("todasLasHorasYDatosReserva/all")
	public DTO todosLasHoras(HttpServletRequest request) {

		DTO dto = new DTO();
		dto.put("result", "fail");
		try {
			// Obtengo el id del usuario a través del token del request del cliente
			// int idUsuAutenticado =
			// AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Listado de cometidos (entidad) gracias al método del repositorio

			List<Horario> horas = (List<Horario>) this.horaRep.findAll();

			List<Reserva> reservas = (List<Reserva>) this.reservaRep.findAll();

			int plazas = 0;

			// Listado de DTO de cometidos que se va a enviar al cliente
			List<DTO> horasDTO = new ArrayList<DTO>();

			List<DTO> reservasDTO = new ArrayList<DTO>();
			List<DTO> plazasDTO = new ArrayList<DTO>();
			// Recorremos la lista de entidad cometidos y se la añadimos a la lista DTO
			// cometidos

			for (Reserva e : reservas) {
				reservasDTO.add(getDatosReserva(e));

			}

			for (Horario h : horas) {
				horasDTO.add(getHoras(h));
			}

			dto.put("horas", horasDTO);
			dto.put("reservas", reservasDTO);
			// dto.put("plazas", plazas);
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
		dto.put("id_actividad", e.getActividad().getIdActividad());
		dto.put("disponible", e.getDisponible());

		return dto;

	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	private DTO getSoloIdHora(Reserva e) {
		DTO dto = new DTO();
		dto.put("id_hora", e.getHorario().getIdHorario());

		return dto;

	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	private DTO getDatosReserva(Reserva e) {
		DTO dto = new DTO();
		dto.put("id_reservas", e.getIdReservas());
		long suma = reservaRep.getSumaPlazas(e.getHorario().getIdHorario());
		dto.put("id_hora", e.getHorario().getIdHorario());
		dto.put("plazas", e.getPlazas());
		dto.put("sumaHoras", suma);

		return dto;

	}

	/**
	 * 
	 * @param datos
	 * @return
	 */

	@GetMapping("reserva/nuevaReserva")
	public DTO nuevaReserva(int id_hora, HttpServletRequest request) {

		DTO dto = new DTO(); // Voy a devolver un dto
		dto.put("result", "fail"); // Asumo que voy a fallar, si todo va bien se sobrescribe este valor

		try {
			// Localizo el usuario autenticado
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Obtengo el usuario autenticado, por su JWT
			Usuario usuAutenticado = this.usuRep.findById(idUsuAutenticado).get();
			Reserva re = reservaRep.getComprobarReserva(id_hora, idUsuAutenticado);
			if (re != null) {
				System.out.println("NOOOOOOOOOOOO");

			} else {
				Reserva r = new Reserva();
				r.setHorario(horaRep.findById(id_hora).get());
				r.setUsuario(usuAutenticado);
				r.setPlazas(1);
				r.setFecha(new Date());

				this.reservaRep.save(r);

				dto.put("result", "ok");
			}
			dto.put("result", "ok");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dto;
	}

	/**
	 * 
	 */

	@GetMapping("reserva/comprobacion")
	public DTO comprobarReserva(int id_hora, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto
		dto.put("result", "fail"); // Asumo que voy a fallar, si todo va bien se sobrescribe este valor

		try {
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Obtengo el usuario autenticado, por su JWT
			Usuario usuAutenticado = this.usuRep.findById(idUsuAutenticado).get();
			Reserva re = reservaRep.getComprobarReserva(id_hora, idUsuAutenticado);
			if (re != null) {
				dto.put("existe", re.getActivo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	@GetMapping("reserva/misReservas")
	public DTO misReservas(HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto

		try {
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Obtengo el usuario autenticado, por su JWT
			//Usuario usuAutenticado = this.usuRep.findById(idUsuAutenticado).get();
			//Reserva re = reservaRep.getComprobarReserva(id_usu, idUsuAutenticado);
			List<DTO> misReservas = (List<DTO>) this.reservaRep.getMisReservas(idUsuAutenticado);
			//List<DTO> misReservasDTO = new ArrayList<DTO>();

			dto.put("misReservas", misReservas);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	/**
	 * 
	 */
	@GetMapping("reserva/grupos")
	public DTO getGrupos(int id_hora, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto

		try {
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Obtengo el usuario autenticado, por su JWT
			//Usuario usuAutenticado = this.usuRep.findById(idUsuAutenticado).get();
			//Reserva re = reservaRep.getComprobarReserva(id_usu, idUsuAutenticado);
			List<DTO> grupos = (List<DTO>) this.reservaRep.getGrupos(id_hora);
			//List<DTO> misReservasDTO = new ArrayList<DTO>();

			dto.put("grupos", grupos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	/**
	 * 
	 * @param datosNuevo
	 * @param request
	 * @return
	 */
	
	@GetMapping("/reserva/updateHora")
	public DTO updateHoraReserva(int id_hora, int id_reservas ,HttpServletRequest request) {
		
		DTO dto = new DTO();
		
		dto.put("result", "fail");
		try {
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			//Usuario u = this.usuRep.findById(idUsuAutenticado).get();
			Reserva r = this.reservaRep.findById(id_reservas).get();
			r.setHorario(this.horaRep.findById(id_hora).get());
			r.setFecha(new Date());
			this.reservaRep.save(r);
			dto.put("result", "ok");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dto;
		
	}

	@DeleteMapping("reserva/delete")
	public DTO deleteReserva(int id_reservas, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto

		try {
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Obtengo el usuario autenticado, por su JWT
			this.reservaRep.deleteById(id_reservas);

			dto.put("result", "ok");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
}

/**
 * Clase interna que contiene los datos de autenticacion del usuario
 */
class DatosReserva {
	int id_hora;

	/**
	 * Constructor
	 */
	public DatosReserva(int id_hora) {
		super();
		this.id_hora = id_hora;

	}
}

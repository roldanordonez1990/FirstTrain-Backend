package com.firsttrain_backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.firsttrain_backend.jwtSecurity.AutenticadorJWT;
import com.firsttrain_backend.model.entities.Usuario;
import com.firsttrain_backend.model.repositories.NivelRepository;
import com.firsttrain_backend.model.repositories.UsuarioRepository;

@CrossOrigin
@RestController
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuRep;
	@Autowired
	NivelRepository nivelRep;
	
	//Buscar todos los usuarios 
	@GetMapping("usuario/all")
	public Iterable<Usuario> encuentraUsuario() {

		return this.usuRep.findAll();
	}
	
	/**
	 * 
	 */
	
	//Método principal con el que obtengo un jwt del usuario logueado. Dentro del jwt obtenido va el id del usuario
	@PostMapping("usuario/autenticadoJWT")
	public DTO usuarioAutenticadoJWT(@RequestBody DatosUsuario datos) {

		DTO dto = new DTO();
		Usuario uAutenticado = (Usuario) usuRep.findByDniAndPassword(datos.dni, datos.password);
		if (uAutenticado != null) {
			dto.put("jwt", AutenticadorJWT.codificaJWT(uAutenticado));
		}
		// Lo que devuelve es el jwt creado con un id del usuario en su interior
		return dto;

		}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	
	@GetMapping("usuario/getDatos")
	public DTO usuarioAutenticado(HttpServletRequest request) {

		DTO dto = new DTO();
		int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);

		Usuario u = this.usuRep.findById(idUsuAutenticado).get();
		dto.put("id_usuario", u.getIdUsuario());
		dto.put("nombre", u.getNombre());
		dto.put("apellidos", u.getApellidos());
		return dto;

	}
	
	/**
	 * 
	 */
	
	
	@GetMapping("/usuario/autenticadoImagen")
	public DTO getUsuarioAutenticado (boolean foto, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto
		int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request); // Obtengo el usuario autenticado, por su JWT

		// Intento localizar un usuario a partir de su id
		Usuario usuAutenticado = usuRep.findById(idUsuAutenticado).get();
		if (usuAutenticado != null) {
			
			dto.put("nombre", usuAutenticado.getNombre());
			dto.put("apellidos", usuAutenticado.getApellidos());
			dto.put("email", usuAutenticado.getEmail());
			dto.put("foto", foto? usuAutenticado.getFoto() : "");
		}

		// Finalmente devuelvo el JWT creado, puede estar vacío si la autenticación no ha funcionado
		return dto;
	}
	
	
	@PostMapping("/usuario/nuevoRegistro")
	public DTO nuevoUsuarioRegistrado(@RequestBody DatosUsuarioNuevoRegistro datosNuevo) {
		
		DTO dto = new DTO();
		
		dto.put("result", "fail");
		try {
			Usuario u = new Usuario();
			
			u.setRol(2);
			u.setNombre(datosNuevo.nombre);
			u.setApellidos(datosNuevo.apellidos);
			u.setTelefono(datosNuevo.telefono);
			u.setEdad(datosNuevo.edad);
			u.setDireccion(datosNuevo.direccion);
			u.setDni(datosNuevo.dni);
			u.setInfoAdicional(datosNuevo.info);
			u.setNivelEntrenamiento(nivelRep.findById(datosNuevo.nivel).get());
			u.setPassword(datosNuevo.password);
			u.setFoto(null);
			u.setEmail(datosNuevo.email);
			this.usuRep.save(u);
			dto.put("result", "ok");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dto;
		
	}


}

/**
 * 
 */


/**
 * Clase interna que contiene los datos de autenticacion del usuario
 */
	class DatosUsuario {
		String dni;
		String password;
	
		/**
		 * Constructor
		 */
		public DatosUsuario(String dni, String password) {
			super();
			this.dni = dni;
			this.password = password;
		}
	}
	
	/**
	 * Clase interna que contiene los datos de autenticacion del usuario
	 */
		class DatosUsuarioNavigation {
			String nombre;
			String apellido;
			
			/**
			 * Constructor
			 */
			public DatosUsuarioNavigation(String nombre, String apellido) {
				super();
				this.nombre = nombre;
				this.apellido = apellido;
			}
		}
		
		/**
		 * Clase interna que contiene los datos de autenticacion del usuario
		 */
		class DatosUsuarioNuevoRegistro {
			String nombre;
			String apellidos;
			String dni;
			String email;
			String telefono;
			String direccion;
			String edad;
			int nivel;
			String password;
			String info;

			/**
			 * Constructor
			 */
			public DatosUsuarioNuevoRegistro(String nombre, String apellidos, String dni, 
					String email, String telefono, String direccion, String edad, 
					int nivel, String password, String info) {
				super();
				this.nombre = nombre;
				this.apellidos = apellidos;
				this.dni = dni;
				this.email = email;
				this.telefono = telefono;
				this.direccion = direccion;
				this.edad = edad;
				this.nivel = nivel;
				this.password = password;
				this.info = info;
			}
		}

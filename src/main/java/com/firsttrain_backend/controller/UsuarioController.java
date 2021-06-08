package com.firsttrain_backend.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.firsttrain_backend.jwtSecurity.AutenticadorJWT;
import com.firsttrain_backend.model.entities.Horario;
import com.firsttrain_backend.model.entities.Reserva;
import com.firsttrain_backend.model.entities.Usuario;
import com.firsttrain_backend.model.repositories.NivelRepository;
import com.firsttrain_backend.model.repositories.UsuarioRepository;
import com.firsttrain_backend.model.services.MailService;

@CrossOrigin
@Controller
@RestController
public class UsuarioController {

	@Autowired
	UsuarioRepository usuRep;
	@Autowired
	NivelRepository nivelRep;
	@Autowired
	private MailService mailService;

	// Buscar todos los usuarios
	@GetMapping("usuario/all")
	public Iterable<Usuario> encuentraUsuario() {

		return this.usuRep.findAll();
	}

	/**
	 * 
	 */

	// Método principal con el que obtengo un jwt del usuario logueado. Dentro del
	// jwt obtenido va el id del usuario
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
		dto.put("rol", u.getRol());
		dto.put("nombre", u.getNombre());
		dto.put("apellidos", u.getApellidos());
		dto.put("telefono", u.getTelefono());
		dto.put("edad", u.getEdad());
		dto.put("direccion", u.getDireccion());
		dto.put("dni", u.getDni());
		dto.put("info", u.getInfoAdicional());
		// dto.put("nivel", u.getNivelEntrenamiento());
		dto.put("password", u.getPassword());
		dto.put("email", u.getEmail());
		return dto;

	}

	@GetMapping("usuario/todasLosDatosUsuarios")
	public DTO usuarioTodosLosDatos(HttpServletRequest request) {

		DTO dto = new DTO();
		dto.put("result", "fail");

		try {
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);

			List<DTO> usuarios = (List<DTO>) this.usuRep.getDatosTodosLosUsuarios();

			dto.put("todasLosDatosUsuario", usuarios);
			// dto.put("plazas", plazas);
			dto.put("result", "ok");

		} catch (Exception e) {
			// TODO: handle exception
		}

		return dto;

	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	private DTO getDatosUsu(Usuario u) {
		DTO dto = new DTO();
		dto.put("id_usuario", u.getIdUsuario());
		dto.put("rol", u.getRol());
		dto.put("nombre", u.getNombre());
		dto.put("apellidos", u.getApellidos());
		dto.put("telefono", u.getTelefono());
		dto.put("edad", u.getEdad());
		dto.put("direccion", u.getDireccion());
		dto.put("dni", u.getDni());
		dto.put("info", u.getInfoAdicional());
		dto.put("nivel", u.getNivelEntrenamiento());
		dto.put("password", u.getPassword());
		dto.put("email", u.getEmail());

		return dto;

	}

	/**
	 * 
	 */

	@GetMapping("/usuario/autenticadoImagen")
	public DTO getUsuarioAutenticado(int id_usu, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto
		// int idUsuAutenticado =
		// AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request); // Obtengo
		// el usuario autenticado, por su JWT

		// Intento localizar un usuario a partir de su id
		Usuario usuAutenticado = usuRep.findById(id_usu).get();
		if (usuAutenticado != null) {
			dto.put("id_usuario", usuAutenticado.getIdUsuario());
			dto.put("nombre", usuAutenticado.getNombre());
			dto.put("apellidos", usuAutenticado.getApellidos());
			dto.put("email", usuAutenticado.getEmail());
			dto.put("password", usuAutenticado.getPassword());
			dto.put("edad", usuAutenticado.getEdad());
			dto.put("direccion", usuAutenticado.getDireccion());
			dto.put("dni", usuAutenticado.getDni());
			dto.put("info", usuAutenticado.getInfoAdicional());
			dto.put("telefono", usuAutenticado.getTelefono());
			dto.put("nivel", usuAutenticado.getNivelEntrenamiento().getIdNivelEntrenamiento());

		}

		// Finalmente devuelvo el JWT creado, puede estar vacío si la autenticación no
		// ha funcionado
		return dto;
	}

	/**
	 * 
	 * @param datosNuevo
	 * @return
	 */

	@GetMapping("/usuario/autenticadoImagen2")
	public DTO getUsuarioAutenticado2(HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto
		int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request); // Obtengo
		// el usuario autenticado, por su JWT

		// Intento localizar un usuario a partir de su id
		Usuario usuAutenticado = usuRep.findById(idUsuAutenticado).get();
		if (usuAutenticado != null) {
			dto.put("id_usuario", usuAutenticado.getIdUsuario());
			dto.put("nombre", usuAutenticado.getNombre());
			dto.put("apellidos", usuAutenticado.getApellidos());
			dto.put("email", usuAutenticado.getEmail());
			dto.put("password", usuAutenticado.getPassword());
			dto.put("edad", usuAutenticado.getEdad());
			dto.put("direccion", usuAutenticado.getDireccion());
			dto.put("dni", usuAutenticado.getDni());
			dto.put("info", usuAutenticado.getInfoAdicional());
			dto.put("telefono", usuAutenticado.getTelefono());
			dto.put("nivel", usuAutenticado.getNivelEntrenamiento().getIdNivelEntrenamiento());

		}

		// Finalmente devuelvo el JWT creado, puede estar vacío si la autenticación no
		// ha funcionado
		return dto;
	}

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String from, String to, String subject, String body) {

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(body);

		javaMailSender.send(mail);
	}

	@PostMapping("/usuario/nuevoRegistro")
	public DTO nuevoUsuarioRegistrado(@RequestBody DatosUsuarioNuevoRegistro datosNuevo) {

		DTO dto = new DTO();

		dto.put("result", "fail");
		try {
			Usuario u = new Usuario();

			String pass = getMD5(datosNuevo.password);
			u.setRol(2);
			u.setNombre(datosNuevo.nombre);
			u.setApellidos(datosNuevo.apellidos);
			u.setTelefono(datosNuevo.telefono);
			u.setEdad(datosNuevo.edad);
			u.setDireccion(datosNuevo.direccion);
			u.setDni(datosNuevo.dni);
			u.setInfoAdicional(datosNuevo.info);
			u.setNivelEntrenamiento(nivelRep.findById(datosNuevo.nivel).get());
			u.setPassword(pass);
			u.setFoto(null);
			u.setEmail(datosNuevo.email);
			this.usuRep.save(u);
			String message = "\n¡Enhorabuena! Ahora formas parde de First Train Center" + "\nTus datos de acceso a la aplicación son los siguientes: " 
			+ "\nClave de acceso: " + datosNuevo.dni + "\nPassword: " + datosNuevo.password;
			mailService.sendMail("roldanordonez.francisco@gmail.com", datosNuevo.email, "Credenciales First-Train", message);
			System.out.println(message);
			dto.put("result", "ok");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;

	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	//Método para codificar en MD5
	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param datos
	 * @return
	 */

	@PostMapping("usuario/comprobacion")
	public DTO comprobarReserva(@RequestBody DatosUsuarioNuevoRegistro datosNuevo) {
		DTO dto = new DTO(); // Voy a devolver un dto
		dto.put("result", "fail"); // Asumo que voy a fallar, si todo va bien se sobrescribe este valor

		try {
			// int idUsuAutenticado =
			// AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Obtengo el usuario autenticado, por su JWT
			List<Usuario> usuAutenticado = this.usuRep.getDniUser();
			String dniNuevo = datosNuevo.dni;
			for (Usuario u : usuAutenticado) {
				if (dniNuevo.equals(u.getDni())) {
					dto.put("existe", usuAutenticado);
				} else {
					dto.put("Noexiste", "fail");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@PostMapping("/usuario/updateUser")
	public DTO updateHoraReserva(@RequestBody DatosUpdateUsu datos) {

		DTO dto = new DTO();

		dto.put("result", "fail");
		try {
			Usuario u = this.usuRep.findById(datos.id_usuario).get();

			// u.setRol(2);
			u.setNombre(datos.nombre);
			u.setApellidos(datos.apellidos);
			u.setTelefono(datos.telefono);
			u.setEdad(datos.edad);
			u.setDireccion(datos.direccion);
			u.setDni(datos.dni);
			u.setInfoAdicional(datos.info);
			u.setNivelEntrenamiento(nivelRep.findById(datos.nivel).get());
			// u.setPassword(datos.password);
			u.setFoto(null);
			u.setEmail(datos.email);
			this.usuRep.save(u);
			dto.put("result", "ok");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dto;

	}

	/**
	 * 
	 */

	@DeleteMapping("usuario/delete")
	public DTO deleteUsuario(int id_usuario, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto

		try {
			int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
			// Obtengo el usuario autenticado, por su JWT
			this.usuRep.deleteById(id_usuario);

			dto.put("result", "ok");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	/**
	 * 
	 */

	@PostMapping("/usuario/ratificaPassword")
	public DTO ratificaPassword(@RequestBody DTO dtoRecibido, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto
		dto.put("result", "fail"); // Asumo que voy a fallar, si todo va bien se sobrescribe este valor

		int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request); // Obtengo el usuario
																									// autenticado, por
																									// su JWT

		try {
			Usuario usuarioAutenticado = usuRep.findById(idUsuAutenticado).get(); // Localizo todos los datos del
																					// usuario
			String password = (String) dtoRecibido.get("password"); // Compruebo la contraseña
			if (password.equals(usuarioAutenticado.getPassword())) {
				dto.put("result", "ok"); // Devuelvo éxito, las contraseñas son iguales
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return dto;
	}

	/**
	 * 
	 */

	@PostMapping("/usuario/modificaPassword")
	public DTO modificaPassword(@RequestBody DTO dtoRecibido, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto
		dto.put("result", "fail"); // Asumo que voy a fallar, si todo va bien se sobrescribe este valor

		int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request); // Obtengo el usuario
																									// autenticado, por
																									// su JWT

		try {
			Usuario usuarioAutenticado = usuRep.findById(idUsuAutenticado).get(); // Localizo al usuario
			String dniUsuario;
			String emailUsuario;
			emailUsuario = usuarioAutenticado.getEmail();
			dniUsuario = usuarioAutenticado.getDni();
			String password = (String) dtoRecibido.get("password"); // Recibo la password que llega en el dtoRecibido
			String passwordMD5 = getMD5(password);
			usuarioAutenticado.setPassword(passwordMD5); // Modifico la password
			usuRep.save(usuarioAutenticado); // Guardo el usuario, con nueva password, en la unidad de persistencia
			String message = "\n¡Enhorabuena! Tu contraseña ha sido modificada correctamente" + "\nAhora tus datos de acceso a la aplicación son los siguientes: " 
					+ "\nClave de acceso: " + dniUsuario + "\nPassword: " + password;
			mailService.sendMail("roldanordonez.francisco@gmail.com", emailUsuario, "Credenciales First-Train Nueva Password", message);
			dto.put("result", "ok"); // Devuelvo éxito
		} catch (Exception ex) {
			ex.printStackTrace();
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
	public DatosUsuarioNuevoRegistro(String nombre, String apellidos, String dni, String email, String telefono,
			String direccion, String edad, int nivel, String password, String info) {
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

/**
 * Clase interna que contiene los datos de autenticacion del usuario
 */
class DatosUpdateUsu {
	int id_usuario;
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
	public DatosUpdateUsu(int id_usuario, String nombre, String apellidos, String dni, String email, String telefono,
			String direccion, String edad, int nivel, String password, String info) {
		super();
		this.id_usuario = id_usuario;
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

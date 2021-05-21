package com.firsttrain_backend.model.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_usuario")
	private int idUsuario;

	private String apellidos;

	private String direccion;

	private String dni;

	private String edad;

	private String email;

	@Lob
	private byte[] foto;

	@Column(name="info_adicional")
	private String infoAdicional;

	private String nombre;

	private String password;

	private int rol;

	private String telefono;

	//bi-directional many-to-one association to Foro
	@OneToMany(mappedBy="usuario")
	@JsonIgnore
	private List<Foro> foros;

	//bi-directional many-to-one association to Reserva
	@OneToMany(mappedBy="usuario")
	@JsonIgnore
	private List<Reserva> reservas;

	//bi-directional many-to-one association to NivelEntrenamiento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="nivel")
	@JsonIgnore
	private NivelEntrenamiento nivelEntrenamiento;

	public Usuario() {
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEdad() {
		return this.edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getInfoAdicional() {
		return this.infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRol() {
		return this.rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Foro> getForos() {
		return this.foros;
	}

	public void setForos(List<Foro> foros) {
		this.foros = foros;
	}

	public Foro addForo(Foro foro) {
		getForos().add(foro);
		foro.setUsuario(this);

		return foro;
	}

	public Foro removeForo(Foro foro) {
		getForos().remove(foro);
		foro.setUsuario(null);

		return foro;
	}

	public List<Reserva> getReservas() {
		return this.reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public Reserva addReserva(Reserva reserva) {
		getReservas().add(reserva);
		reserva.setUsuario(this);

		return reserva;
	}

	public Reserva removeReserva(Reserva reserva) {
		getReservas().remove(reserva);
		reserva.setUsuario(null);

		return reserva;
	}

	public NivelEntrenamiento getNivelEntrenamiento() {
		return this.nivelEntrenamiento;
	}

	public void setNivelEntrenamiento(NivelEntrenamiento nivelEntrenamiento) {
		this.nivelEntrenamiento = nivelEntrenamiento;
	}

}
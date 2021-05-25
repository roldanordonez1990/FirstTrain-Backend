package com.firsttrain_backend.model.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the reserva database table.
 * 
 */
@Entity
@NamedQuery(name="Reserva.findAll", query="SELECT r FROM Reserva r")
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_reservas")
	private int idReservas;

	private boolean activo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	private int plazas;

	//bi-directional many-to-one association to Horario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_hora")
	@JsonIgnore
	private Horario horario;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usu")
	@JsonIgnore
	private Usuario usuario;

	public Reserva() {
	}

	public int getIdReservas() {
		return this.idReservas;
	}

	public void setIdReservas(int idReservas) {
		this.idReservas = idReservas;
	}

	public boolean getActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getPlazas() {
		return this.plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	public Horario getHorario() {
		return this.horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
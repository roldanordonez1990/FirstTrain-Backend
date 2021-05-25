package com.firsttrain_backend.model.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the horario database table.
 * 
 */
@Entity
@NamedQuery(name="Horario.findAll", query="SELECT h FROM Horario h")
public class Horario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_horario")
	private int idHorario;

	private boolean disponible;

	private String horas;

	//bi-directional many-to-one association to Actividad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ref_actividades")
	@JsonIgnore
	private Actividad actividad;

	//bi-directional many-to-one association to Reserva
	@OneToMany(mappedBy="horario")
	@JsonIgnore
	private List<Reserva> reservas;

	public Horario() {
	}

	public int getIdHorario() {
		return this.idHorario;
	}

	public void setIdHorario(int idHorario) {
		this.idHorario = idHorario;
	}

	public boolean getDisponible() {
		return this.disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public String getHoras() {
		return this.horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public List<Reserva> getReservas() {
		return this.reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public Reserva addReserva(Reserva reserva) {
		getReservas().add(reserva);
		reserva.setHorario(this);

		return reserva;
	}

	public Reserva removeReserva(Reserva reserva) {
		getReservas().remove(reserva);
		reserva.setHorario(null);

		return reserva;
	}

}
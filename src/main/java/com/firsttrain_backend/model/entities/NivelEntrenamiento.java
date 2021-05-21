package com.firsttrain_backend.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the nivel_entrenamiento database table.
 * 
 */
@Entity
@Table(name="nivel_entrenamiento")
@NamedQuery(name="NivelEntrenamiento.findAll", query="SELECT n FROM NivelEntrenamiento n")
public class NivelEntrenamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_nivel_entrenamiento")
	private int idNivelEntrenamiento;

	@Column(name="descripcion_nivel")
	private String descripcionNivel;

	@Column(name="nivel_corto")
	private String nivelCorto;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="nivelEntrenamiento")
	private List<Usuario> usuarios;

	public NivelEntrenamiento() {
	}

	public int getIdNivelEntrenamiento() {
		return this.idNivelEntrenamiento;
	}

	public void setIdNivelEntrenamiento(int idNivelEntrenamiento) {
		this.idNivelEntrenamiento = idNivelEntrenamiento;
	}

	public String getDescripcionNivel() {
		return this.descripcionNivel;
	}

	public void setDescripcionNivel(String descripcionNivel) {
		this.descripcionNivel = descripcionNivel;
	}

	public String getNivelCorto() {
		return this.nivelCorto;
	}

	public void setNivelCorto(String nivelCorto) {
		this.nivelCorto = nivelCorto;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setNivelEntrenamiento(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setNivelEntrenamiento(null);

		return usuario;
	}

}
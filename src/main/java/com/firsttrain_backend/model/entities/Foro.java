package com.firsttrain_backend.model.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the foro database table.
 * 
 */
@Entity
@NamedQuery(name="Foro.findAll", query="SELECT f FROM Foro f")
public class Foro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_mensaje")
	private int idMensaje;

	private boolean dislike;

	@Lob
	private byte[] imagen;

	private boolean like;

	private String mensaje;

	@Column(name="sum_dislike")
	private int sumDislike;

	@Column(name="sum_like")
	private int sumLike;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public Foro() {
	}

	public int getIdMensaje() {
		return this.idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

	public boolean getDislike() {
		return this.dislike;
	}

	public void setDislike(boolean dislike) {
		this.dislike = dislike;
	}

	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public boolean getLike() {
		return this.like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getSumDislike() {
		return this.sumDislike;
	}

	public void setSumDislike(int sumDislike) {
		this.sumDislike = sumDislike;
	}

	public int getSumLike() {
		return this.sumLike;
	}

	public void setSumLike(int sumLike) {
		this.sumLike = sumLike;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
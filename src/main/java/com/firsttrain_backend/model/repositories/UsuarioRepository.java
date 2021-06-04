package com.firsttrain_backend.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.firsttrain_backend.controller.DTO;
import com.firsttrain_backend.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	
	public Usuario findByNombreAndApellidos(String nombre, String apellidos);
	public Usuario findByNombreAndPassword(String nombre, String password);
	public Usuario findByDniAndPassword(String dni, String password);
	public Usuario findByDni(String dni);
	
	@Query(value="SELECT id_usuario, nombre, rol, apellidos, telefono, edad, direccion, dni, info_adicional,"
			+ "nivel, password, email, ni.nivel_corto FROM usuario as u, nivel_entrenamiento as ni "
			+ "WHERE u.nivel = ni.id_nivel_entrenamiento ORDER BY rol asc", nativeQuery = true)
	public List<DTO> getDatosTodosLosUsuarios();
	
	@Query(value="SELECT * from usuario", nativeQuery = true)
	public List<Usuario> getDniUser();

}

package com.firsttrain_backend.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.firsttrain_backend.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	
	public Usuario findByNombreAndApellidos(String nombre, String apellidos);
	public Usuario findByNombreAndPassword(String nombre, String password);
	public Usuario findByDniAndPassword(String dni, String password);

}

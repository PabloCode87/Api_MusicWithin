package com.ejemplos.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.Cancion;
import com.ejemplos.modelo.Comentario;
import com.ejemplos.modelo.Roles;
import com.ejemplos.modelo.Usuario;
import com.ejemplos.repository.CancionRepositorio;
import com.ejemplos.repository.ComentarioRepositorio;
import com.ejemplos.repository.RolesRepositorio;
import com.ejemplos.repository.UsuarioRepositorio;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
	@Autowired
	private CancionRepositorio cancionRepositorio;
	
	@Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolesRepositorio rolesRepositorio;
	
	public List<Comentario> obtenerTodosLosComentarios(){
		return this.comentarioRepositorio.findAll();
	}
	
	public List<Comentario> obtenerComentariosPorCancion(Long cancionID) {
        return comentarioRepositorio.encontrarPorCancionOrdenadoPorFechaAsc(cancionID);
    }

	public Comentario guardarComentario(Long cancionID, Long userID, Long roleID, Comentario comentario) {
	    Cancion cancion = this.cancionRepositorio.findById(cancionID).orElse(null);
	    comentario.setSong(cancion);

	    Usuario usuario=this.usuarioRepositorio.findById(userID).orElse(null);
	    Roles rol=this.rolesRepositorio.findById(roleID).orElse(null);
	    comentario.setUser(usuario);
	    comentario.setRole(rol);
	    comentario.setCreation_date(LocalDateTime.now());

	    return comentarioRepositorio.save(comentario);
	}

    public void borrarComentario(Long commentID) {
        comentarioRepositorio.deleteById(commentID);
    }
}

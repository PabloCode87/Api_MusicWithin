package com.ejemplos.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.Evento;
import com.ejemplos.modelo.Usuario;
import com.ejemplos.repository.EventoRepositorio;

@Service
public class EventoService {

	@Autowired
	private EventoRepositorio eventoRepositorio;
	
	@Autowired
	private UsuarioService usuarioServicio;
	
	public List<Evento> obtenerTodos() {
        return eventoRepositorio.findAll();
    }
	
	public Evento obtenerEventoPorID(Long eventoID) {
		return this.eventoRepositorio.findById(eventoID).orElse(null);
	}
	
	public List<Evento> obtenerEventosPorUserID(Long userID) {
        return eventoRepositorio.findAllByUserID(userID);
    }
	
	public List<Usuario> obtenerUsuariosAsistentes(Long eventoID) {
        List<Long> usuariosIDs = this.eventoRepositorio.findUsersAttendingEvent(eventoID);
        return this.usuarioServicio.obtenerUsuariosPorIDs(usuariosIDs);
    }
	
	public Evento modificarEvento(Long eventoID, Evento eventoModificado) {
	    Evento eventoExistente = eventoRepositorio.findById(eventoID).orElse(null);
	    if (eventoExistente != null) {
	        eventoExistente.setNombre_evento(eventoModificado.getNombre_evento());
	        eventoExistente.setFecha_creacion(eventoModificado.getFecha_creacion());
	        eventoExistente.setLugar_evento(eventoModificado.getLugar_evento());
	        return eventoRepositorio.save(eventoExistente);
	    } else {
	        return null;
	    }
	}
	
	public Evento crearEvento(Evento nuevoEvento) {
	    return eventoRepositorio.save(nuevoEvento);
	}
	
	public void eliminarEvento(Long eventoID) {
        List<Long> usuariosIDs = eventoRepositorio.findUsersAttendingEvent(eventoID);
        for (Long userID : usuariosIDs) {
            usuarioServicio.eliminarAsistenciaAEvento(userID, eventoID);
        }
        eventoRepositorio.deleteById(eventoID);
    }
	
	public void agregarUsuarioAEvento(Long eventoID, Long userID) {
	    try {
	        Evento evento = eventoRepositorio.findById(eventoID).orElse(null);
	        Usuario usuario = usuarioServicio.obtenerPorId(userID);
	        if (evento == null) {
	            throw new Exception("Evento no encontrado");
	        }
	        if (usuario == null) {
	            throw new Exception("Usuario no encontrado");
	        }
	        eventoRepositorio.insertUserToEvent(eventoID, userID);
	    } catch (Exception e) {
	        throw new RuntimeException("Error al agregar usuario al evento");
	    }
	}
	
	public void eliminarUsuarioDeEvento(Long eventoID, Long userID) {
		  try {
		    eventoRepositorio.deleteUserFromEvent(eventoID, userID);
		  } catch (Exception e) {
		    throw new RuntimeException("Error al eliminar usuario del evento.");
		  }
	}
	
	//filtrado
	public List<Evento> buscarEventos(String nombreEvento, Date fechaCreacion, String lugarEvento) {
	    List<Evento> resultados = new ArrayList<>();

	    if (nombreEvento != null && fechaCreacion != null && lugarEvento != null) {
	      resultados.addAll(eventoRepositorio.findEventosPorNombreEventoFechaCreacionYLugarEvento(nombreEvento, fechaCreacion, lugarEvento));
	    } else if (nombreEvento != null && fechaCreacion != null) {
	      resultados.addAll(eventoRepositorio.findEventosByNombreEventoYFechaCreacion(nombreEvento, fechaCreacion));
	    } else if (nombreEvento != null && lugarEvento != null) {
	      resultados.addAll(eventoRepositorio.findEventosByNombreEventoYLugarEvento(nombreEvento, lugarEvento));
	    } else if (fechaCreacion != null && lugarEvento != null) {
	      resultados.addAll(eventoRepositorio.findEventosByFechaCreacionYLugarEvento(fechaCreacion, lugarEvento));
	    } else if (nombreEvento != null) {
	      resultados.addAll(eventoRepositorio.findEventosByNombreEvento(nombreEvento));
	    } else if (fechaCreacion != null) {
	      resultados.addAll(eventoRepositorio.findEventosByFechaCreacion(fechaCreacion));
	    } else if (lugarEvento != null) {
	      resultados.addAll(eventoRepositorio.findEventosByLugarEvento(lugarEvento));
	    }

	    return resultados;
	  }
}

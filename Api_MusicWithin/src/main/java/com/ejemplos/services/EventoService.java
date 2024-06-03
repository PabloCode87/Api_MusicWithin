package com.ejemplos.services;

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
}

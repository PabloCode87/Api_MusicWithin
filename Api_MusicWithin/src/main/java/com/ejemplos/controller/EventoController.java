package com.ejemplos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.modelo.Evento;
import com.ejemplos.modelo.Usuario;
import com.ejemplos.repository.RolesRepositorio;
import com.ejemplos.services.EventoService;
import com.ejemplos.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:4200")
public class EventoController {

	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/evento")
	public ResponseEntity<?> obtenerTodosLosEventos() {
	    List<Evento>eventos=this.eventoService.obtenerTodos();
	    return ResponseEntity.ok(eventos);
    }
	
	@GetMapping("/evento/{eventoID}")
	public ResponseEntity<?> obtenerEventoPorID(@PathVariable Long eventoID) {
	    Evento evento=this.eventoService.obtenerEventoPorID(eventoID);
	    return ResponseEntity.ok(evento);
    }
	
	@GetMapping("/usuario/{userID}/eventos")
    public ResponseEntity<?> obtenerEventosPorUserID(@PathVariable Long userID) {
        List<Evento> eventos = eventoService.obtenerEventosPorUserID(userID);
        return ResponseEntity.ok(eventos);
    }
	
	@GetMapping("/{eventoID}/usuarios")
    public ResponseEntity<?> obtenerUsuariosAsistentes(@PathVariable Long eventoID) {
        List<Usuario> usuarios = this.eventoService.obtenerUsuariosAsistentes(eventoID);
        return ResponseEntity.ok(usuarios);
    }
	
	@PutMapping("/evento/{eventoID}")
    public ResponseEntity<?> modificarEvento(@PathVariable Long eventoID, @RequestBody Evento evento) {
        Evento eventoModificado = this.eventoService.modificarEvento(eventoID, evento);
        return ResponseEntity.ok(eventoModificado);
    }
	
	@PostMapping("/evento")
	public ResponseEntity<?> crearEvento(@RequestBody Evento nuevoEvento) {
	    Evento eventoCreado = this.eventoService.crearEvento(nuevoEvento);
	    if (eventoCreado != null) {
	        return ResponseEntity.ok(eventoCreado);
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@DeleteMapping("/evento/{eventoID}")
	public ResponseEntity<?> eliminarEvento(@PathVariable Long eventoID) {
	    eventoService.eliminarEvento(eventoID);
	    return ResponseEntity.ok().build();
	}
}

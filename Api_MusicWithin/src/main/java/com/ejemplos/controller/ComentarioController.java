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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.modelo.Comentario;
import com.ejemplos.services.AlbumService;
import com.ejemplos.services.CancionService;
import com.ejemplos.services.ComentarioService;
import com.ejemplos.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioService;
	
	@PostMapping("/comentario/cancion/{cancionID}")
	public ResponseEntity<Comentario> agregarComentario(@PathVariable Long cancionID, @RequestParam Long userID, @RequestParam Long roleID, @RequestBody Comentario comentario) {
	    Comentario nuevoComentario = comentarioService.guardarComentario(cancionID, userID, roleID, comentario);
	    return ResponseEntity.status(HttpStatus.CREATED).body(nuevoComentario);
	}

    @GetMapping("/comentario/cancion/{cancionID}/comentarios")
    public ResponseEntity<List<Comentario>> obtenerComentariosPorCancion(@PathVariable Long cancionID) {
        List<Comentario> comentarios = comentarioService.obtenerComentariosPorCancion(cancionID);
        return ResponseEntity.ok().body(comentarios);
    }

    @DeleteMapping("/comentario/{commentID}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long commentID) {
        comentarioService.borrarComentario(commentID);
        return ResponseEntity.noContent().build();
    }
}

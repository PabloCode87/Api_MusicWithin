package com.ejemplos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.modelo.Album;
import com.ejemplos.services.AlbumService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:4200")
public class AlbumController {

	private final AlbumService albumService;
	
	@GetMapping("/album")
	public ResponseEntity<?> obtenerTodos(){
		List<Album> albumnes=this.albumService.obtenerTodos();
		return ResponseEntity.ok().body(albumnes);
	}
	
	@GetMapping("/album/{albumID}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long albumID){
		Album albumNuevo=this.albumService.obtenerPorId(albumID);
		return ResponseEntity.ok().body(albumNuevo);
	}
	
	@PostMapping("/album")
	public ResponseEntity<?>insertarAlbum(@RequestBody Album album){
		Album albumNuevo=this.albumService.insertarAlbum(album);
		return ResponseEntity.ok().body(albumNuevo);
	}
	
	@PutMapping("album/{albumID}")
	public ResponseEntity<?>actualizarAlbum(@PathVariable Long albumID, @RequestBody Album album){
		Album albumActualizado=this.albumService.actualizarAlbum(albumID, album);
		return ResponseEntity.ok().body(albumActualizado);
	}
	
	@DeleteMapping("album/{albumID}")
	public ResponseEntity<?>eliminarAlbum(@PathVariable Long albumID){
		this.albumService.eliminarAlbum(albumID);
		return ResponseEntity.ok().build();
	}
}

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.modelo.Playlist;
import com.ejemplos.modelo.Usuario;
import com.ejemplos.services.PlaylistService;
import com.ejemplos.services.PlaylistSongService;
import com.ejemplos.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:4200")
public class PlaylistController {

	private final PlaylistService playlistService;
	
	private final UsuarioService usuarioService;
	
	private final PlaylistSongService playlistSongService;
	
	@GetMapping("/playlist")
	public ResponseEntity<?> obtenerTodos(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Playlist> playlists = this.playlistService.obtenerTodos(pageable);
		return ResponseEntity.ok().body(playlists);
	}
	
	@GetMapping("/playlist/{playlistID}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long playlistID){
		Playlist playlistNuevo=this.playlistService.obtenerPorId(playlistID);
		return ResponseEntity.ok().body(playlistNuevo);
	}
	
	@PostMapping("/playlist")
	public ResponseEntity<?> insertarPlaylist(@RequestBody Playlist playlistRequest) {
	    Playlist playlist = new Playlist();
	    playlist.setPlaylist_name(playlistRequest.getPlaylist_name());
	    playlist.setDescription(playlistRequest.getDescription());
	    playlist.setCreation_date(playlistRequest.getCreation_date());
	    playlist.setStatus(playlistRequest.getStatus());

	    Usuario usuario = usuarioService.obtenerPorId(playlistRequest.getUsuario().getUserID());
	    playlist.setUsuario(usuario);
	    this.playlistService.insertarPlaylist(playlist);
	    return ResponseEntity.ok().body(playlist);
	}
	
	@PutMapping("playlist/{playlistID}")
	public ResponseEntity<?>actualizarPlaylist(@PathVariable Long playlistID, @RequestBody Playlist Playlist){
		Playlist playlistActualizado=this.playlistService.actualizarPlaylist(playlistID, Playlist);
		return ResponseEntity.ok().body(playlistActualizado);
	}
	
	@DeleteMapping("playlist/{playlistID}")
	public ResponseEntity<?>eliminarCancion(@PathVariable Long playlistID){
		this.playlistService.eliminarPlaylist(playlistID);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/playlist/{playlistID}/cancion/{cancionID}")
	public ResponseEntity<?> agregarCancionAPlaylist(@PathVariable Long playlistID, @PathVariable Long cancionID) {
	    playlistSongService.agregarCancionAPlaylist(playlistID, cancionID);
	    return ResponseEntity.ok().build();
	}
	
	@GetMapping("/playlist/usuario/{usuarioID}")
	public ResponseEntity<?> obtenerPlaylistsPorUsuarioID(@PathVariable Long usuarioID) {
	    List<Playlist> playlists = playlistService.obtenerPorUsuarioID(usuarioID);
	    return ResponseEntity.ok().body(playlists);
	}
	
	@GetMapping("/playlist/buscar")
    public ResponseEntity<?> buscarPlaylists(
        @RequestParam(required = false) String playlist_name,
        @RequestParam(required = false) String status) {
        List<Playlist> playlists = playlistService.buscarPorNombreYEstado(playlist_name, status);
        return ResponseEntity.ok().body(playlists);
    }
	
}

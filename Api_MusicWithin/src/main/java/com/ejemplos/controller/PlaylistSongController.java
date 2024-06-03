package com.ejemplos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.modelo.Cancion;
import com.ejemplos.modelo.PlaylistSong;
import com.ejemplos.services.PlaylistSongService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:4200")
public class PlaylistSongController {

	private final PlaylistSongService playlistSongService;
	
	@GetMapping("/playlistsong")
    public ResponseEntity<?> obtenerTodos() {
        List<PlaylistSong> playlists = this.playlistSongService.obtenerTodos();
        return ResponseEntity.ok().body(playlists);
    }

    @GetMapping("/playlistsong/{followerID}/{followedID}")
    public ResponseEntity<?> obtenerUno(@PathVariable Long playlistID, @PathVariable Long songID) {
    	PlaylistSong playlistSongNuevo=this.playlistSongService.obtenerPorId(playlistID, songID);
        return ResponseEntity.ok().body(playlistSongNuevo);
    }
    
    @GetMapping("/playlistsong/{playlistID}")
    public ResponseEntity<?> obtenerPorPlaylistID(@PathVariable Long playlistID) {
        List<PlaylistSong> playlistSongs = this.playlistSongService.obtenerPorPlaylistID(playlistID);
        return ResponseEntity.ok().body(playlistSongs);
    }
    
    @GetMapping("/playlistsong/{playlistID}/canciones")
    public ResponseEntity<List<Cancion>> getCancionesByPlaylistID(@PathVariable Long playlistID) {
        List<Cancion> canciones = playlistSongService.findCancionesByPlaylistID(playlistID);
        return ResponseEntity.ok().body(canciones);
    }
    
    @DeleteMapping("/playlistsong/{playlistID}/{songID}")
    public ResponseEntity<?> eliminarCancionDePlaylist(@PathVariable Long playlistID, @PathVariable Long songID) {
        playlistSongService.eliminarCancionDePlaylist(playlistID, songID);
        return ResponseEntity.ok().build();
    }
}

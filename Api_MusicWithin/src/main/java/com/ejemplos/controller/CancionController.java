package com.ejemplos.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ejemplos.modelo.Album;
import com.ejemplos.modelo.Cancion;
import com.ejemplos.modelo.Usuario;
import com.ejemplos.services.AlbumService;
import com.ejemplos.services.CancionService;
import com.ejemplos.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:4200")
public class CancionController {
	
	private final CancionService cancionService;
	
	private final AlbumService albumService;
	
	private final UsuarioService usuarioService;
	
	@GetMapping("/cancion")
	public ResponseEntity<?> obtenerTodos(){
		List<Cancion> canciones=this.cancionService.obtenerTodos();
		return ResponseEntity.ok().body(canciones);
	}
	
	@GetMapping("/cancion/{cancionID}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long cancionID){
		Cancion cancionNuevo=this.cancionService.obtenerPorId(cancionID);
		return ResponseEntity.ok().body(cancionNuevo);
	}
	
	@PostMapping("/cancion")
    public ResponseEntity<?> insertarCancion(@RequestParam("song_name") String songName,
                                             @RequestParam("artist") String artist,
                                             @RequestParam("genre") String genre,
                                             @RequestParam("duration") Time duration,
                                             @RequestParam("albumID") Long albumID,
                                             @RequestParam("audio_file") MultipartFile audioFile,
                                             @RequestParam("userID") Long userID) throws IOException {
        if (audioFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor, seleccione un archivo de audio");
        }
        Album album = albumService.obtenerPorId(albumID);
        if (album == null) {
            return ResponseEntity.badRequest().body("Álbum no encontrado");
        }
        Usuario usuario=this.usuarioService.obtenerPorId(userID);
        
        String audioBase64 = Base64.getEncoder().encodeToString(audioFile.getBytes());

        Cancion cancion = new Cancion();
        cancion.setSong_name(songName);
        cancion.setArtist(artist);
        cancion.setGenre(genre);
        cancion.setDuration(duration);
        cancion.setAudio_file(audioBase64);
        cancion.setAlbum(album);
        cancion.setUploadedBy(usuario);
        Cancion cancionNueva = this.cancionService.insertarCancion(cancion);
        return ResponseEntity.ok().body(cancionNueva);
    }
	
	@PutMapping("/cancion/{cancionID}")
	public ResponseEntity<?> actualizarCancion(@PathVariable Long cancionID,
	                                           @RequestBody Cancion cancion) throws IOException {
	    Cancion cancionExistente = cancionService.obtenerPorId(cancionID);
	    if (cancionExistente == null) {
	        return ResponseEntity.badRequest().body("Canción no encontrada");
	    }

	    cancionExistente.setSong_name(cancion.getSong_name());
	    cancionExistente.setArtist(cancion.getArtist());
	    cancionExistente.setGenre(cancion.getGenre());
	    cancionExistente.setDuration(cancion.getDuration());
	    Cancion cancionActualizada = cancionService.actualizarCancion(cancionID, cancionExistente);
	    
	    return ResponseEntity.ok().body(cancionActualizada);
	}
	
	@DeleteMapping("cancion/{cancionID}")
	public ResponseEntity<?>eliminarCancion(@PathVariable Long cancionID){
		this.cancionService.eliminarCancion(cancionID);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/cancionPlaylist/{cancionID}")
	public ResponseEntity<?> eliminarCancionDePlaylist(@PathVariable Long cancionID) {
		cancionService.eliminarReferenciasEnComentarios(cancionID);
	    cancionService.eliminarReferenciasEnPlaylists(cancionID);
	    cancionService.eliminarCancion(cancionID);
	    return ResponseEntity.ok().build();
	}
	
	@GetMapping("cancion/buscar-cancion")
	public ResponseEntity<?> buscarCancion(@RequestParam(required = false) String song_name) {
	    List<Cancion> resultados = cancionService.buscarPorNombre(song_name);
	    return ResponseEntity.ok().body(resultados);
	}
	
	@GetMapping("/cancion/buscar")
	public ResponseEntity<?> buscarCancionPorFiltros(@RequestParam(required = false) String song_name,
	                                                  @RequestParam(required = false) String artist,
	                                                  @RequestParam(required = false) String genre) {
	    if (song_name == null && artist == null && genre == null) {
	        List<Cancion> todasLasCanciones = cancionService.obtenerTodos();
	        return ResponseEntity.ok().body(todasLasCanciones);
	    }
	    List<Cancion> resultados = cancionService.buscarPorFiltros(song_name, artist, genre);
	    return ResponseEntity.ok().body(resultados);
	}
	
	@GetMapping("/cancion/generos")
	public ResponseEntity<?>todosLosGeneros(){
		List<String>generos=this.cancionService.todosLosGeneros();
		return ResponseEntity.ok().body(generos);
	}
	
	@GetMapping("/cancion/usuario/{userID}")
	public ResponseEntity<?> encontrarCancionesSubidasPorUsuario(@PathVariable Long userID) {
	    Usuario usuario = usuarioService.obtenerPorId(userID);
	    if (usuario == null) {
	        return ResponseEntity.badRequest().body("Usuario no encontrado");
	    }
	    List<Cancion> canciones = cancionService.encontrarCancionesSubidasPorUsuario(usuario);
	    return ResponseEntity.ok().body(canciones);
	}
	
	@GetMapping("/cancion/{cancionID}/uploader")
    public ResponseEntity<?> obtenerIdUsuarioPorIdCancion(@PathVariable Long cancionID) {
        Long userID = cancionService.obtenerIdUsuarioPorIdCancion(cancionID);
        if (userID != null) {
            return ResponseEntity.ok().body(userID);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	//para hacer un gzip en caso de que lo necesite
	public String encodeAndCompressAudioFile(byte[] audioFile) {
        String encoded = Base64.getEncoder().encodeToString(audioFile);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(encoded.getBytes());
            gzip.close();
            byte[] compressed = bos.toByteArray();
            bos.close();
            return Base64.getEncoder().encodeToString(compressed);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}

package com.ejemplos.services;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.Cancion;
import com.ejemplos.modelo.Usuario;
import com.ejemplos.repository.CancionRepositorio;
import com.ejemplos.repository.ComentarioRepositorio;
import com.ejemplos.repository.PlaylistSongRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CancionService {
	
	@Autowired
	private CancionRepositorio cancionRepositorio;
	
	@Autowired
    private PlaylistSongRepositorio playlistSongRepository;
	
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
	public List<Cancion> obtenerTodos(){
		return this.cancionRepositorio.findAll();
	}
	
	public Cancion obtenerPorId(Long songId) {
		Cancion cancion=this.cancionRepositorio.findById(songId).orElse(null);
		return cancion;
	}
	
	public Cancion insertarCancion(Cancion cancion) {
		return this.cancionRepositorio.save(cancion);
	}
	
	public Cancion actualizarCancion(Long songId, Cancion cancion) {
		Cancion cancionExistente= this.cancionRepositorio.findById(songId).orElse(null);
		if(cancionExistente != null) {
			cancion.setSongID(songId);
			return this.cancionRepositorio.save(cancion);
		}
		return null;
	}
	
	public void eliminarCancion(Long id) {
		this.cancionRepositorio.deleteById(id);
	}
	
	public void eliminarReferenciasEnPlaylists(Long cancionID) {
        this.playlistSongRepository.deleteBySongID(cancionID);
    }
	
	public void eliminarReferenciasEnComentarios(Long cancionID) {
        this.comentarioRepositorio.deleteBySongSongID(cancionID);
    }
	
    public String encodeAudioFile(byte[] audioFile) {
        return Base64.getEncoder().encodeToString(audioFile);
    }
    
    public List<Cancion> buscarPorNombre(String song_name) {
        return cancionRepositorio.buscarPorNombre(song_name);
    }
    
    public List<Cancion> buscarPorFiltros(String song_name, String artist, String genre) {
        if (song_name != null && artist != null && genre != null) {
            return cancionRepositorio.encontrarPorNombreCancionArtistaYGenero(song_name, artist, genre);
        } else if (song_name != null && artist != null) {
            return cancionRepositorio.encontrarPorNombreCancionYArtista(song_name, artist);
        } else if (song_name != null && genre != null) {
            return cancionRepositorio.encontrarPorNombreCancionYGenero(song_name, genre);
        } else if (artist != null && genre != null) {
            return cancionRepositorio.encontrarPorArtistaYGenero(artist, genre);
        } else if (song_name != null) {
            return cancionRepositorio.buscarPorNombre(song_name);
        } else if (artist != null) {
            return cancionRepositorio.encontrarPorArtista(artist);
        } else if (genre != null) {
            return cancionRepositorio.encontrarPorGenero(genre);
        } else {
            return cancionRepositorio.findAll();
        }
    }
    
    public List<String>todosLosGeneros(){
    	List<String>generos=this.cancionRepositorio.todosLosGeneros();
    	return generos;
    }
    
    public List<Cancion> encontrarCancionesSubidasPorUsuario(Usuario usuario) {
        return cancionRepositorio.encontrarCancionesSubidasPorUsuario(usuario);
    }

    public Long obtenerIdUsuarioPorIdCancion(Long songId) {
        return cancionRepositorio.findUploadedByUserIdBySongId(songId);
    }


}

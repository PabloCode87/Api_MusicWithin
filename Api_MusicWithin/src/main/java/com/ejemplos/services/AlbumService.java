package com.ejemplos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.Album;
import com.ejemplos.modelo.Cancion;
import com.ejemplos.repository.AlbumRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {

	@Autowired
	private AlbumRepositorio albumRepositorio;
	
	@Autowired
	private CancionService cancionService;
	
	public List<Album> obtenerTodos(){
		return this.albumRepositorio.findAll();
	}
	
	public Album obtenerPorId(Long id) {
		Album album=this.albumRepositorio.findById(id).orElse(null);
		return album;
	}
	
	public Album insertarAlbum(Album album) {
		return this.albumRepositorio.save(album);
	}
	
	public Album actualizarAlbum(Long id, Album album) {
		Album albumExistente= this.albumRepositorio.findById(id).orElse(null);
		if(albumExistente != null) {
			album.setAlbumID(id);
			return this.albumRepositorio.save(album);
		}
		return null;
	}
	
	public void eliminarAlbum(Long id) {
		this.albumRepositorio.deleteById(id);
	}
	
	public Album moverCancionDeAlbum(Long cancionID, Long albumOrigenID, Long albumDestinoID) {
        Cancion cancion = cancionService.obtenerPorId(cancionID);
        if (cancion == null) {
            return null;
        }
        if (!cancion.getAlbum().getAlbumID().equals(albumOrigenID)) {
            return null;
        }
        Album albumOrigen = albumRepositorio.findById(albumOrigenID).orElse(null);
        Album albumDestino = albumRepositorio.findById(albumDestinoID).orElse(null);
        if (albumOrigen == null || albumDestino == null) {
            return null;
        }
        cancion.setAlbum(albumDestino);
        cancionService.actualizarCancion(cancionID, cancion);

        return albumDestino;
    }
	
	public List<Cancion> findAllSongsByAlbumId(Long albumId) {
        return albumRepositorio.findAllSongsByAlbumId(albumId);
    }
}

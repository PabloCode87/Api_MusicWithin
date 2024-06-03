package com.ejemplos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.Playlist;
import com.ejemplos.modelo.Usuario;
import com.ejemplos.repository.PlaylistRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaylistService {
	
	@Autowired
	private PlaylistRepositorio playlistRepositorio;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Page<Playlist> obtenerTodos(Pageable pageable){
		return this.playlistRepositorio.findAll(pageable);
	}
	
	public Playlist obtenerPorId(Long playlistID) {
		Playlist playlist=this.playlistRepositorio.findById(playlistID).orElse(null);
		return playlist;
	}
	
	public Playlist insertarPlaylist(Playlist playlist) {
		return this.playlistRepositorio.save(playlist);
	}
	
	public Playlist actualizarPlaylist(Long playlistID, Playlist playlist) {
		Playlist playlistExistente= this.playlistRepositorio.findById(playlistID).orElse(null);
		if(playlistExistente != null) {
			playlist.setPlaylistID(playlistID);
			return this.playlistRepositorio.save(playlist);
		}
		return null;
	}
	
	public void eliminarPlaylist(Long playlistID) {
		this.playlistRepositorio.deleteById(playlistID);
	}
	
	public List<Playlist> obtenerPorUsuarioID(Long usuarioID) {
	    Usuario usuario = usuarioService.obtenerPorId(usuarioID);
	    return playlistRepositorio.findByUsuario(usuario);
	}
	
	public List<Playlist> buscarPorNombreYEstado(String playlist_name, String status) {
        return playlistRepositorio.findByPlaylistNameAndStatus(playlist_name, status);
    }

}

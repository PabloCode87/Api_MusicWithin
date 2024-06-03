package com.ejemplos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.Cancion;
import com.ejemplos.modelo.PlaylistSong;
import com.ejemplos.modelo.PlaylistSongId;
import com.ejemplos.repository.PlaylistSongRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaylistSongService {

	@Autowired
	private PlaylistSongRepositorio playlistSongRepositorio;
	
	@Autowired
	private CancionService cancionService;
	
	public List<PlaylistSong> obtenerTodos(){
		return this.playlistSongRepositorio.findAll();
	}
	
	public PlaylistSong obtenerPorId(Long playlistID, Long songID) {
		PlaylistSong playlistSong=this.playlistSongRepositorio.findById(new PlaylistSongId(playlistID, songID)).orElse(null);
		return playlistSong;
	}
	
	public List<PlaylistSong> obtenerPorPlaylistID(Long playlistID) {
        return this.playlistSongRepositorio.findByPlaylistID(playlistID);
    }
	
	public List<Cancion> findCancionesByPlaylistID(Long playlistID) {
        return playlistSongRepositorio.findCancionesByPlaylistID(playlistID);
    }
	
	public void agregarCancionAPlaylist(Long playlistID, Long cancionID) {
	    PlaylistSong playlistSong = new PlaylistSong();
	    playlistSong.setPlaylistID(playlistID);
	    playlistSong.setSongID(cancionID);
	    playlistSongRepositorio.save(playlistSong);
	}
	
	public void eliminarCancionDePlaylist(Long playlistID, Long cancionID) {
        PlaylistSongId id = new PlaylistSongId(playlistID, cancionID);
        playlistSongRepositorio.deleteById(id);
    }
}

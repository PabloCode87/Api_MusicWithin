package com.ejemplos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ejemplos.modelo.Cancion;
import com.ejemplos.modelo.PlaylistSong;
import com.ejemplos.modelo.PlaylistSongId;

import jakarta.transaction.Transactional;

public interface PlaylistSongRepositorio extends JpaRepository<PlaylistSong, PlaylistSongId> {

	List<PlaylistSong> findByPlaylistID(Long playlistID);
	
	@Query("SELECT c FROM Cancion c JOIN PlaylistSong ps ON c.songID = ps.songID WHERE ps.playlistID = :playlistID")
	List<Cancion> findCancionesByPlaylistID(@Param("playlistID") Long playlistID);
	
	@Transactional
    void deleteBySongID(Long songID);
}

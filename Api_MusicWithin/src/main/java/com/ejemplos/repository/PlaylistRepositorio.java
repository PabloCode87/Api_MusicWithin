package com.ejemplos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ejemplos.modelo.Playlist;
import com.ejemplos.modelo.Usuario;

public interface PlaylistRepositorio extends JpaRepository<Playlist, Long> {
	List<Playlist> findByUsuario(Usuario usuario);
	
	@Query("SELECT p FROM Playlist p WHERE (:playlist_name IS NULL OR p.playlist_name LIKE %:playlist_name%) AND (:status IS NULL OR p.status = :status)")
    List<Playlist> findByPlaylistNameAndStatus(
        @Param("playlist_name") String playlist_name,
        @Param("status") String status
    );
}

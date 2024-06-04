package com.ejemplos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ejemplos.modelo.Album;
import com.ejemplos.modelo.Cancion;

public interface AlbumRepositorio extends JpaRepository<Album, Long>{

	@Query("SELECT c FROM Cancion c WHERE c.album.albumID = :albumId")
    List<Cancion> findAllSongsByAlbumId(@Param("albumId") Long albumId);
}

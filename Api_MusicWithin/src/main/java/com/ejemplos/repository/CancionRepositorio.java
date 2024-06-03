package com.ejemplos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ejemplos.modelo.Cancion;
import com.ejemplos.modelo.Usuario;

import jakarta.transaction.Transactional;

public interface CancionRepositorio extends JpaRepository<Cancion, Long> {
	@Query("SELECT c FROM Cancion c WHERE LOWER(c.song_name) LIKE LOWER(concat('%', :song_name, '%'))")
    List<Cancion> buscarPorNombre(@Param("song_name") String song_name);
	
	
	@Query("SELECT c FROM Cancion c WHERE c.uploadedBy = :usuario")
	List<Cancion> encontrarCancionesSubidasPorUsuario(@Param("usuario") Usuario usuario);

	@Query("SELECT c FROM Cancion c WHERE LOWER(c.song_name) LIKE LOWER(concat('%', :song_name, '%')) AND LOWER(c.artist) LIKE LOWER(concat('%', :artist, '%'))")
	List<Cancion> encontrarPorNombreCancionYArtista(@Param("song_name") String song_name, @Param("artist") String artist);

	@Query("SELECT c FROM Cancion c WHERE LOWER(c.song_name) LIKE LOWER(concat('%', :song_name, '%')) AND LOWER(c.genre) LIKE LOWER(concat('%', :genre, '%'))")
	List<Cancion> encontrarPorNombreCancionYGenero(@Param("song_name") String song_name, @Param("genre") String genre);

	@Query("SELECT c FROM Cancion c WHERE LOWER(c.artist) LIKE LOWER(concat('%', :artist, '%')) AND LOWER(c.genre) LIKE LOWER(concat('%', :genre, '%'))")
	List<Cancion> encontrarPorArtistaYGenero(@Param("artist") String artist, @Param("genre") String genre);

	@Query("SELECT c FROM Cancion c WHERE LOWER(c.song_name) LIKE LOWER(concat('%', :song_name, '%')) AND LOWER(c.artist) LIKE LOWER(concat('%', :artist, '%')) AND LOWER(c.genre) LIKE LOWER(concat('%', :genre, '%'))")
	List<Cancion> encontrarPorNombreCancionArtistaYGenero(@Param("song_name") String song_name, @Param("artist") String artist, @Param("genre") String genre);

	@Query("SELECT c FROM Cancion c WHERE LOWER(c.artist) LIKE LOWER(concat('%', :artist, '%'))")
	List<Cancion> encontrarPorArtista(@Param("artist") String artist);

	@Query("SELECT c FROM Cancion c WHERE LOWER(c.genre) LIKE LOWER(concat('%', :genre, '%'))")
	List<Cancion> encontrarPorGenero(@Param("genre") String genre);
	
	@Query("SELECT DISTINCT c.genre FROM Cancion c")
	List<String>todosLosGeneros();
	
	@Query("SELECT c.uploadedBy.userID FROM Cancion c WHERE c.songID = :songID")
    Long findUploadedByUserIdBySongId(@Param("songID") Long songID);

}


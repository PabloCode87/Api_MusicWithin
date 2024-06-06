package com.ejemplos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ejemplos.modelo.Comentario;

import jakarta.transaction.Transactional;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {

    @Query("SELECT c FROM Comentario c WHERE c.song.songID = ?1 ORDER BY c.creation_date DESC")
    List<Comentario> encontrarPorCancionOrdenadoPorFechaAsc(Long cancionID);

    @Transactional
    void deleteBySongSongID(Long songID);
}

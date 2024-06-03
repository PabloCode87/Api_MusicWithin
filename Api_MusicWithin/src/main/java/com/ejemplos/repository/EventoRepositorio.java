package com.ejemplos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ejemplos.modelo.Evento;
import com.ejemplos.modelo.Usuario;

@Repository
public interface EventoRepositorio extends JpaRepository<Evento, Long> {

	List<Evento> findAllByUserID(Long userID);
	
	@Query("SELECT e.userID FROM EventoUsuario e WHERE e.eventoID = ?1")
	List<Long> findUsersAttendingEvent(Long eventoID);
	
	@Modifying
    @Query("DELETE FROM EventoUsuario eu WHERE eu.eventoID = :eventoID AND eu.userID = :userID")
    void deleteAttendanceRecord(@Param("eventoID") Long eventoID, @Param("userID") Long userID);
}

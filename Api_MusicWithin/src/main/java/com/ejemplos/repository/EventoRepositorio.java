package com.ejemplos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ejemplos.modelo.Evento;
import com.ejemplos.modelo.Usuario;

import jakarta.transaction.Transactional;

@Repository
public interface EventoRepositorio extends JpaRepository<Evento, Long> {

	List<Evento> findAllByUserID(Long userID);
	
	@Query("SELECT e.userID FROM EventoUsuario e WHERE e.eventoID = ?1")
	List<Long> findUsersAttendingEvent(Long eventoID);
	
	@Modifying
    @Query("DELETE FROM EventoUsuario eu WHERE eu.eventoID = :eventoID AND eu.userID = :userID")
    void deleteAttendanceRecord(@Param("eventoID") Long eventoID, @Param("userID") Long userID);
	
	@Modifying
	@Transactional
    @Query("INSERT INTO EventoUsuario (eventoID, userID) VALUES (:eventoID, :userID)")
    void insertUserToEvent(@Param("eventoID") Long eventoID, @Param("userID") Long userID);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM EventoUsuario eu WHERE eu.eventoID = :eventoID AND eu.userID = :userID")
	void deleteUserFromEvent(@Param("eventoID") Long eventoID, @Param("userID") Long userID);
	
	//filtrado
	@Query("SELECT e FROM Evento e WHERE LOWER(e.nombre_evento) LIKE LOWER(concat('%', :nombreEvento, '%'))")
    List<Evento> findEventosByNombreEvento(@Param("nombreEvento") String nombreEvento);

    @Query("SELECT e FROM Evento e WHERE e.fecha_creacion = :fechaCreacion")
    List<Evento> findEventosByFechaCreacion(@Param("fechaCreacion") Date fechaCreacion);

    @Query("SELECT e FROM Evento e WHERE LOWER(e.lugar_evento) LIKE LOWER(concat('%', :lugarEvento, '%'))")
    List<Evento> findEventosByLugarEvento(@Param("lugarEvento") String lugarEvento);

    @Query("SELECT e FROM Evento e WHERE LOWER(e.nombre_evento) LIKE LOWER(concat('%', :nombreEvento, '%')) AND e.fecha_creacion = :fechaCreacion")
    List<Evento> findEventosByNombreEventoYFechaCreacion(@Param("nombreEvento") String nombreEvento, @Param("fechaCreacion") Date fechaCreacion);

   @Query("SELECT e FROM Evento e WHERE LOWER(e.nombre_evento) LIKE LOWER(concat('%', :nombreEvento, '%')) AND LOWER(e.lugar_evento) LIKE LOWER(concat('%', :lugarEvento, '%'))")
    List<Evento> findEventosByNombreEventoYLugarEvento(@Param("nombreEvento") String nombreEvento, @Param("lugarEvento") String lugarEvento);

    @Query("SELECT e FROM Evento e WHERE e.fecha_creacion = :fechaCreacion AND LOWER(e.lugar_evento) LIKE LOWER(concat('%', :lugarEvento, '%'))")
    List<Evento> findEventosByFechaCreacionYLugarEvento(@Param("fechaCreacion") Date fechaCreacion, @Param("lugarEvento") String lugarEvento);

    @Query("SELECT e FROM Evento e WHERE LOWER(e.nombre_evento) LIKE LOWER(concat('%', :nombreEvento, '%')) AND e.fecha_creacion = :fechaCreacion AND LOWER(e.lugar_evento) LIKE LOWER(concat('%', :lugarEvento, '%'))")
    List<Evento> findEventosPorNombreEventoFechaCreacionYLugarEvento(@Param("nombreEvento") String nombreEvento, @Param("fechaCreacion") Date fechaCreacion, @Param("lugarEvento") String lugarEvento);
}

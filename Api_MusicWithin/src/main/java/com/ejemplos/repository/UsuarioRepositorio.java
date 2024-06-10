package com.ejemplos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ejemplos.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);
    Usuario findByUsernameAndEmail(String username, String email);
    
    @Query("SELECT u FROM Usuario u WHERE " +
    	       "(:keyword IS NULL OR :keyword = '' OR u.username LIKE %:keyword% OR u.nombre LIKE %:keyword% OR u.apellidos LIKE %:keyword%)")
    List<Usuario> findByUsernameOrNombreOrApellidos(@Param("keyword") String keyword);
    
    List<Usuario> findAllByUserIDIn(List<Long> usuariosIDs);
    
    @Query("SELECT u.role.roleID from Usuario u WHERE u.userID = :userID")
    Long encontrarRoleUsuario(Long userID);
}

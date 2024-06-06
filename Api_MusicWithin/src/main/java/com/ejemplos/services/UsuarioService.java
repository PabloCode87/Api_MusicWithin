package com.ejemplos.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.Roles;
import com.ejemplos.modelo.Usuario;
import com.ejemplos.repository.EventoRepositorio;
import com.ejemplos.repository.UsuarioRepositorio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private EventoRepositorio eventoRepositorio;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

    public List<Usuario> obtenerTodos() {
        return this.usuarioRepositorio.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        Usuario usuario = this.usuarioRepositorio.findById(id).orElse(null);
        return usuario;
    }

    public Usuario findByUsername(String username) {
        return this.usuarioRepositorio.findByUsername(username);
    }
    
    public Long getUserIdByUsername(String username) {
        Usuario usuario = usuarioRepositorio.findByUsername(username);
        if (usuario != null) {
            return usuario.getUserID();
        } else {
            return null;
        }
    }
    
    public Usuario findByUsernameAndEmail(String username, String email) {
        return this.usuarioRepositorio.findByUsernameAndEmail(username, email);
    }

    public Usuario insertarUsuario(Usuario usuario) {
        return this.usuarioRepositorio.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = this.usuarioRepositorio.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuario.setUserID(id);
            usuario.setRole(usuarioExistente.getRole());

            if (!usuarioExistente.getPassword_hash().equals(usuario.getPassword_hash())) {
                String encryptedPassword = passwordEncoder.encode(usuario.getPassword_hash());
                usuario.setPassword_hash(encryptedPassword);
            }
            
            return this.usuarioRepositorio.save(usuario);
        }
        return null;
    }

    public void eliminarUsuario(Long id) {
        this.usuarioRepositorio.deleteById(id);
    }
    
    public List<Usuario> buscarUsuarios(String keyword) {
        return this.usuarioRepositorio.findByUsernameOrNombreOrApellidos(keyword);
    }
    
    public List<Usuario> obtenerUsuariosPorIDs(List<Long> usuariosIDs) {
        return usuarioRepositorio.findAllByUserIDIn(usuariosIDs);
    }
    
    @Transactional
    public void eliminarAsistenciaAEvento(Long eventoID, Long userID) {
        eventoRepositorio.deleteAttendanceRecord(eventoID, userID);
    }
    
    public String determinarTipoUsuario(String username) {
        Usuario usuario = usuarioRepositorio.findByUsername(username);
        if (usuario != null) {
            Roles role = usuario.getRole();
            if (role != null) {
                String roleName = role.getRole_name();
                if (roleName.equals("ADMIN")) {
                    return "ADMIN";
                } else if (roleName.equals("USER")) {
                    return "USER";
                } else if (roleName.equals("SUPERVISOR")) {
                    return "SUPERVISOR";
                } else {
                    return "UNKNOWN";
                }
            }
        }
        return "UNKNOWN";
    }
    
    public Long encontrarRoleUsuario(Long userID) {
    	return this.usuarioRepositorio.encontrarRoleUsuario(userID);
    }

}

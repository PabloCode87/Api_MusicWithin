package com.ejemplos.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.Usuario;
import com.ejemplos.repository.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

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

}

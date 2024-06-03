package com.ejemplos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.Roles;
import com.ejemplos.repository.RolesRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolesService {

	@Autowired
	private RolesRepositorio rolesRepositorio;
	
	public List<Roles> obtenerTodos(){
		return this.rolesRepositorio.findAll();
	}
	
	public Roles obtenerPorId(Long id) {
		Roles roles=this.rolesRepositorio.findById(id).orElse(null);
		return roles;
	}
	
	public Roles insertarRoles(Roles roles) {
		return this.rolesRepositorio.save(roles);
	}
	
	public Roles actualizarRoles(Long id, Roles roles) {
		Roles rolesExistente= this.rolesRepositorio.findById(id).orElse(null);
		if(rolesExistente != null) {
			roles.setRoleID(id);
			return this.rolesRepositorio.save(roles);
		}
		return null;
	}
	
	public void eliminarRoles(Long id) {
		this.rolesRepositorio.deleteById(id);
	}
}

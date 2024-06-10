package com.ejemplos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.modelo.Roles;
import com.ejemplos.services.RolesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RolesController {
	
	private final RolesService rolesService;
	
	@GetMapping("/roles")
	public ResponseEntity<?> obtenerTodos(){
		List<Roles> roles=this.rolesService.obtenerTodos();
		return ResponseEntity.ok().body(roles);
	}
	
	@GetMapping("/roles/{rolesID}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long rolesID){
		Roles rolesNuevo=this.rolesService.obtenerPorId(rolesID);
		return ResponseEntity.ok().body(rolesNuevo);
	}

}

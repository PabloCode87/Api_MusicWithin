package com.ejemplos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.modelo.Subscription;
import com.ejemplos.services.SubscriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
	
	private final SubscriptionService subscriptionService;
	
	@GetMapping("/subscription")
	public ResponseEntity<?> obtenerTodos(){
		List<Subscription> subscriptiones=this.subscriptionService.obtenerTodos();
		return ResponseEntity.ok().body(subscriptiones);
	}
	
	@GetMapping("/subscription/{subscriptionID}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long subscriptionID){
		Subscription subscriptionNuevo=this.subscriptionService.obtenerPorId(subscriptionID);
		return ResponseEntity.ok().body(subscriptionNuevo);
	}

}

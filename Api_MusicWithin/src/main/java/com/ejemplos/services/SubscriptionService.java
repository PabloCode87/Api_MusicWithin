package com.ejemplos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.Subscription;
import com.ejemplos.repository.SubscriptionRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

	@Autowired
	private SubscriptionRepositorio subscriptionRepositorio;
	
	public List<Subscription> obtenerTodos(){
		return this.subscriptionRepositorio.findAll();
	}
	
	public Subscription obtenerPorId(Long id) {
		Subscription subscription=this.subscriptionRepositorio.findById(id).orElse(null);
		return subscription;
	}
	
	public Subscription insertarSubscriptions(Subscription subscription) {
		return this.subscriptionRepositorio.save(subscription);
	}
	
	public Subscription actualizarSubscription(Long id, Subscription subscription) {
		Subscription subscriptionExistente= this.subscriptionRepositorio.findById(id).orElse(null);
		if(subscriptionExistente != null) {
			subscription.setSubscriptionID(id);
			return this.subscriptionRepositorio.save(subscription);
		}
		return null;
	}
	
	public void eliminarSubscription(Long id) {
		this.subscriptionRepositorio.deleteById(id);
	}
}

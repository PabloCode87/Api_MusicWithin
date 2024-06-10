package com.ejemplos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplos.modelo.Subscription;

public interface SubscriptionRepositorio extends JpaRepository<Subscription, Long>{

}

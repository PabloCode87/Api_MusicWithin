package com.ejemplos.modelo;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EventoUsuario")
public class EventoUsuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventoUsuarioID;

    private Long eventoID;
    private Long userID;
}

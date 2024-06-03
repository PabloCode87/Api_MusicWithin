package com.ejemplos.modelo;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventoID;

    @Column(name = "nombre_evento")
    private String nombre_evento;

    @Column(name = "fecha_creacion")
    private Date fecha_creacion;
    
    @Column(name = "lugar_evento")
    private String lugar_evento;

    @Column(name = "userID")
    private Long userID;

    @ManyToMany
    @JoinTable(
            name = "EventoUsuario",
            joinColumns = @JoinColumn(name = "eventoID"),
            inverseJoinColumns = @JoinColumn(name = "userID")
    )
    private List<Usuario> usuariosAsistentes;
}

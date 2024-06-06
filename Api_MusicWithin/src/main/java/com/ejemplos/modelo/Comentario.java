package com.ejemplos.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Comentario")
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentID;

    private String content;

    private LocalDateTime creation_date;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Usuario user;

    @ManyToOne
    @JoinColumn(name = "songID")
    private Cancion song;

    @ManyToOne
    @JoinColumn(name = "roleID")
    private Roles role;
}

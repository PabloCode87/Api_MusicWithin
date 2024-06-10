package com.ejemplos.modelo;

import java.io.Serializable;
import java.sql.Time;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "Cancion")
public class Cancion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songID;

    private String song_name;

    private String artist;

    private String genre;

    private Time duration;

    @ManyToOne
    @JoinColumn(name = "albumID")
    private Album album;
    
    @Column(name = "audio_file", columnDefinition = "LONGTEXT")
    private String audio_file;
    
    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private Usuario uploadedBy;
}

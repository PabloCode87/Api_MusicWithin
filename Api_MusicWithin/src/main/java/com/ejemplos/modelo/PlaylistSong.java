package com.ejemplos.modelo;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@IdClass(PlaylistSongId.class)
public class PlaylistSong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long playlistID;

    @Id
    private Long songID;

}

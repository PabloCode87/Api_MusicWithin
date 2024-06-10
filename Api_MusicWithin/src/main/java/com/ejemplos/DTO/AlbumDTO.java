package com.ejemplos.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlbumDTO {

	private Long albumID;
    private String album_name;
    private String artist;
    private Integer year;
}

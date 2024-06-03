package com.ejemplos.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateAlbumDTO {

	private Long albumID;
    private String album_name;
    private String artist;
    private Integer year;
}

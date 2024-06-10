package com.ejemplos.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ejemplos.modelo.Album;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlbumDTOConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public AlbumDTO ConvertirADto(Album album) {
		return modelMapper.map(album, AlbumDTO.class);
	}
	
	public Album convertirAAlbum(CreateAlbumDTO createAlbumDto) {
		return modelMapper.map(createAlbumDto, Album.class);
	}
}

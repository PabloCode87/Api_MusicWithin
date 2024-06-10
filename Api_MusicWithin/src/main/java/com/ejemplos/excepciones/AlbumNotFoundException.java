package com.ejemplos.excepciones;

public class AlbumNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public AlbumNotFoundException(Long albumID) {
		super("No se pudo encontrar el album con ID: "+albumID);
	}

}

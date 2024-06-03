package com.ejemplos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplos.modelo.Album;

public interface AlbumRepositorio extends JpaRepository<Album, Long>{

}

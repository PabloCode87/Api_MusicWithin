package com.ejemplos.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.modelo.UserFollowsUser;
import com.ejemplos.services.UserFollowsUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:4200")
public class UserFollowUserController {

	private final UserFollowsUserService userFollowsUserService;
	
	@GetMapping("/userFollowUser")
    public ResponseEntity<?> obtenerTodos() {
        List<UserFollowsUser> relaciones = userFollowsUserService.obtenerTodos();
        return ResponseEntity.ok().body(relaciones);
    }

    @GetMapping("/userFollowUser/{followerID}/{followedID}")
    public ResponseEntity<?> obtenerUno(@PathVariable Long followerID, @PathVariable Long followedID) {
        UserFollowsUser userFollowUserNuevo=this.userFollowsUserService.obtenerPorId(followerID, followedID);
        return ResponseEntity.ok().body(userFollowUserNuevo);
    }
    

    @PostMapping("/follow/{followerID}/{followedID}")
    public ResponseEntity<?> seguirUsuario(@PathVariable Long followerID, @PathVariable Long followedID) {
        Date followDate = new Date();
        userFollowsUserService.seguirUsuario(followerID, followedID, followDate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unfollow/{followerID}/{followedID}")
    public ResponseEntity<?> dejarDeSeguirUsuario(@PathVariable Long followerID, @PathVariable Long followedID) {
        userFollowsUserService.dejarDeSeguirUsuario(followerID, followedID);
        return ResponseEntity.ok().build();
    }
}

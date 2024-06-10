package com.ejemplos.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.modelo.UserFollowsUser;
import com.ejemplos.modelo.UserFollowsUserId;

import com.ejemplos.repository.UserFollowsUserRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFollowsUserService {

	@Autowired
	private UserFollowsUserRepositorio userFollowsUserRepositorio;
	
	public List<UserFollowsUser> obtenerTodos(){
		return this.userFollowsUserRepositorio.findAll();
	}
	
	public UserFollowsUser obtenerPorId(Long followerID, Long followedID) {
		UserFollowsUser userFollowsUser=this.userFollowsUserRepositorio.findById(new UserFollowsUserId(followerID, followedID)).orElse(null);
		return userFollowsUser;
	}
	
    public void seguirUsuario(Long followerID, Long followedID, Date followDate) {
        UserFollowsUser userFollowsUser = new UserFollowsUser(followerID, followedID, followDate);
        userFollowsUserRepositorio.save(userFollowsUser);
    }

    public void dejarDeSeguirUsuario(Long followerID, Long followedID) {
        userFollowsUserRepositorio.deleteById(new UserFollowsUserId(followerID, followedID));
    }
}

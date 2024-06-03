package com.ejemplos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplos.modelo.UserFollowsUser;
import com.ejemplos.modelo.UserFollowsUserId;

public interface UserFollowsUserRepositorio  extends JpaRepository<UserFollowsUser, UserFollowsUserId>{

}

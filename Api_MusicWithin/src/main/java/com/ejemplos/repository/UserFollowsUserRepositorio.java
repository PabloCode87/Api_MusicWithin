package com.ejemplos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ejemplos.modelo.UserFollowsUser;
import com.ejemplos.modelo.UserFollowsUserId;

public interface UserFollowsUserRepositorio  extends JpaRepository<UserFollowsUser, UserFollowsUserId>{

	@Query("SELECT ufu.followedID FROM UserFollowsUser ufu WHERE ufu.followerID = :followerID")
    List<Long> findFollowedUsersByFollowerID(@Param("followerID") Long followerID);
}

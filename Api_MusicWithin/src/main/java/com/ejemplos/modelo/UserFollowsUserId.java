package com.ejemplos.modelo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserFollowsUserId implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long followerID;
    private Long followedID;
}

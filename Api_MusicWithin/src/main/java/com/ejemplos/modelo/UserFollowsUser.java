package com.ejemplos.modelo;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@IdClass(UserFollowsUserId.class)
public class UserFollowsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long followerID;

    @Id
    private Long followedID;

    private Date follow_date;

}

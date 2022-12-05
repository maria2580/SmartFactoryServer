package com.primitive.SmartFactoryServer.DAO.users;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class UsersDAO {
    @Id
    @Column(name = "userIndex")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long index;
    @Column(nullable = false)
    String userId;
    @Column(nullable = false)
    String pw;
    String pushToken;

    public UsersDAO(String id, String PW) {
        this.userId = id;
        this.pw = PW;
    }
    public void updatePushToken(String pushToken){
        this.pushToken=pushToken;
    }

}

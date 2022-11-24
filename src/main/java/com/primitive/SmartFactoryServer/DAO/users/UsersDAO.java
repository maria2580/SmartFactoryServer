package com.primitive.SmartFactoryServer.DAO.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Entity
@NoArgsConstructor
public class UsersDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long index;
    @Column(nullable = false)
    String userId;
    @Column(nullable = false)
    String pw;
    String factoryToken;
    String clientToken;

    public UsersDAO(String id, String PW) {
        this.userId = id;
        this.pw = PW;
    }

    public void updateFactoryToken(){
        
    }
    public void updateClientToken(){

    }
    public boolean clientTokenCheck(String clientToken){
        return this.clientToken.equals(clientToken);
    }
    public boolean factoryTokenCheck(String FactoryToken){
        return this.factoryToken.equals(factoryToken);
    }
}

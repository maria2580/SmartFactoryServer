package com.primitive.SmartFactoryServer.DAO.SensorValues;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class SensorValueDAO extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long index;
    @Column(columnDefinition = "LONG",nullable = false)
    UsersDAO user;
    @Column(columnDefinition = "TEXT", nullable = false)
    String sensorValues;

    @Builder
    public SensorValueDAO(UsersDAO user,String sensorValues) {
        this.user = user;//새 값 추가할 때는 userID로 index 구해서 매개변수로 삽입하기.
        this.sensorValues = sensorValues;
    }
}

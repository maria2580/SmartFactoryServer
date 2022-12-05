package com.primitive.SmartFactoryServer.DAO.SensorValues;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Id;


import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="SensorValueDAO")
public class SensorValueDAO extends BaseTimeEntity {
    @Id
    @Column(name = "sensorValueIndex")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIndex")
    private UsersDAO user;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String sensorValues;

    @Builder
    public SensorValueDAO(UsersDAO user, String sensorValues) {
        this.user = user;//새 값 추가할 때는 userID로 index 구해서 매개변수로 삽입하기.
        this.sensorValues = sensorValues;
    }
}

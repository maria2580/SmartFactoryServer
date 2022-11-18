package com.primitive.SmartFactoryServer.DAO.Alarms;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import com.primitive.SmartFactoryServer.DAO.Sensors.SensorDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@NoArgsConstructor
public class AlarmDAO extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long index; // 알람 구분 인덱스
    @Column(columnDefinition = "LONG",nullable = false)
    UsersDAO user;
    @Column(columnDefinition = "LONG",nullable = false)
    SensorDAO sensorIndex; // 센서 고유 인덱스
    // on/off알람일경우 ?
    @Column(columnDefinition = "TEXT", nullable = false)
    double from; // 알람 최저 기준치
    @Column(columnDefinition = "TEXT", nullable = false)
    double to; // 알람 최고 기준치

    public void updateFrom(Double from){
        this.from = from;
    }
    public void updateTo(Double to){
        this.to = to;
    }

    @Builder
    public AlarmDAO(UsersDAO usersDAO,SensorDAO sensorIndex ,double from, double to) {
        this.user = usersDAO;//새 값 추가할 때는 userID로 index 구해서 매개변수로 삽입하기.
        this.sensorIndex = sensorIndex;
        this.from = from;
        this.to = to;

        //Todo 센서 값은 RTDB에, 센서 명령어를 DB에 저장하여 참조 가능하도록 변경
    }
}

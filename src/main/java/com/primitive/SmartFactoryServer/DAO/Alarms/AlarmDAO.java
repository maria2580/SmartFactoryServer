package com.primitive.SmartFactoryServer.DAO.Alarms;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
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
    Long index;
    @Column(columnDefinition = "LONG",nullable = false)
    UsersDAO user;
    @Column(columnDefinition = "LONG",nullable = false)
    SensorValueDAO sensorIndex;
    @Column(columnDefinition = "TEXT", nullable = false)
    String from;
    @Column(columnDefinition = "TEXT", nullable = false)
    String to;

    @Builder
    public AlarmDAO(UsersDAO usersDAO) {
        this.user = usersDAO;//새 값 추가할 때는 userID로 index 구해서 매개변수로 삽입하기.
        //Todo 센서 값은 RTDB에, 센서 명령어를 DB에 저장하여 참조 가능하도록 변경
    }
}

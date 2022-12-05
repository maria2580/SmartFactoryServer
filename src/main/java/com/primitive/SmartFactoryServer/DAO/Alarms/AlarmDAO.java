package com.primitive.SmartFactoryServer.DAO.Alarms;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import com.primitive.SmartFactoryServer.DAO.Sensors.SensorDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class AlarmDAO extends BaseTimeEntity {
    @Id
    @Column(name="alarmIndex")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index; // 알람 구분 인덱스
    @ManyToOne(fetch = FetchType.LAZY) // 1
    @JoinColumn(name = "user")
    private UsersDAO user;
    @ManyToOne(fetch = FetchType.LAZY) // 1
    @JoinColumn(name = "sensor")
    private SensorDAO sensor; // 센서 고유 인덱스
    @Column(columnDefinition = "DOUBLE", nullable = false)
    private double minimum; // 알람 최저 기준치
    @Column(columnDefinition = "DOUBLE", nullable = false)
    private double maximum; // 알람 최고 기준치

    public void updateMinimum(Double minimum){
        this.minimum = minimum;
    }
    public void updateMaximum(Double maximum){
        this.maximum = maximum;
    }

    @Builder
    public AlarmDAO(UsersDAO usersDAO, SensorDAO sensor, double minimum, double maximum) {
        this.user = usersDAO;//새 값 추가할 때는 userID로 index 구해서 매개변수로 삽입하기.
        this.sensor = sensor;
        this.minimum = minimum;
        this.maximum = maximum;

        //Todo 센서 값은 RTDB에, 센서 명령어를 DB에 저장하여 참조 가능하도록 변경
    }
}

package com.primitive.SmartFactoryServer.DAO.SensorValues;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@NoArgsConstructor
public class SensorValueDAO extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long index;
    @Column(length = 30,nullable = false)
    Long userIndex;
    @Column(columnDefinition = "TEXT", nullable = false)
    String sensorValues;

    @Builder
    public SensorValueDAO(Long userIndex,String sensorValues) {
        this.userIndex = userIndex;//새 값 추가할 때는 userID로 index 구해서 매개변수로 삽입하기.
        this.sensorValues = sensorValues;
    }
}

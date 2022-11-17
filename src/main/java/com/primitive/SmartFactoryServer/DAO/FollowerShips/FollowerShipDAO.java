package com.primitive.SmartFactoryServer.DAO.FollowerShips;

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
public class FollowerShipDAO extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long index;
    @Column(columnDefinition = "LONG",nullable = false)
    Long userIndex;
    @Column(columnDefinition = "TEXT", nullable = false)
    String sensorValues;

    @Builder
    public FollowerShipDAO(Long userIndex,String sensorValues) {
        this.userIndex = userIndex;//새 값 추가할 때는 userID로 index 구해서 매개변수로 삽입하기.
        this.sensorValues = sensorValues;
    }
}
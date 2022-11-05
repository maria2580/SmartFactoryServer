package com.primitive.SmartFactoryServer.DTO;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


public class SensorValueDTO {

    Long index;
    String userID;
    String sensorName;
    String sensorValue;
    public SensorValueDTO(String userID, String sensorName, String sensorValue) {
        this.userID = userID;
        this.sensorName=sensorName;
        this.sensorValue = sensorValue;
    }
}

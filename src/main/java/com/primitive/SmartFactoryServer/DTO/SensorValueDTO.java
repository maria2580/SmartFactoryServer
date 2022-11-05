package com.primitive.SmartFactoryServer.DTO;


import lombok.Getter;
@Getter
public class SensorValueDTO {
    String sensorName;
    String sensorValue;
    public SensorValueDTO(String sensorName, String sensorValue) {
        this.sensorName=sensorName;
        this.sensorValue = sensorValue;
    }
}

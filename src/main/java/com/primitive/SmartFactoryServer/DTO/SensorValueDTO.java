package com.primitive.SmartFactoryServer.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorValueDTO {
    private SensorValue[] sensorValues;
    private String ID;
    private String loginToken;
}

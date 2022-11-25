package com.primitive.SmartFactoryServer.DTO;


import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class SensorValueDTO {
    String sensorName;
    String sensorValue;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public SensorValueDTO(String sensorName, String sensorValue, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.sensorName = sensorName;
        this.sensorValue = sensorValue;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}

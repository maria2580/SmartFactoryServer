package com.primitive.SmartFactoryServer.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@ToString
@Getter
public class SensorValueDTO {
    private SensorValue[] sensorValues;
    private String ID;

    public String valueToString() {
        String s="";
        for(int i =0; i<sensorValues.length;i++){
            s=s+sensorValues[i].toString();
            if(i<sensorValues.length-1){
                s=s+",";
            }

        }
        return "[" + s +"]";
    }
}

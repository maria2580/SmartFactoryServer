package com.primitive.SmartFactoryServer.DTO;


import lombok.Getter;

import java.io.Serializable;

@Getter
public class SensorValue implements Serializable {
    private String name;
    private String value;

    public SensorValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":" +"\""+ name + "\","+"\"value\":" +"\""+ value + "\"" +
                '}';
    }
}

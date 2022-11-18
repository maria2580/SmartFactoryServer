package com.primitive.SmartFactoryServer.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
public class SensorDTO implements Serializable {
    private long index;
    private String name;
    private String command;
    public SensorDTO(long index, String name, String command) {
        this.index=index;
        this.name = name;
        this.command = command;
    }


}

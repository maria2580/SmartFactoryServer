package com.primitive.SmartFactoryServer.DTO;

import lombok.Getter;

@Getter
public class Sensor {
    private long index;
    private String name;
    private String command;
    public Sensor(long index, String name, String command) {
        this.index=index;
        this.name = name;
        this.command = command;
    }


}

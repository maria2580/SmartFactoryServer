package com.primitive.SmartFactoryServer.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorDTO {
    private String name;
    private String command;
    private String user_id;
}

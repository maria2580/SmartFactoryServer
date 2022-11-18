package com.primitive.SmartFactoryServer.DAO.Sensors;

import com.primitive.SmartFactoryServer.DTO.SensorDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@NoArgsConstructor
public class SensorDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long index;
    @Column(nullable = false)
    Long userIndex;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String command;
    public void change(SensorDTO sensorDTO){
        this.name=sensorDTO.getName();
        this.command=sensorDTO.getCommand();
    }
    @Builder
    public SensorDAO(Long userIndex,String name, String command) {
        this.userIndex=userIndex;
        this.name = name;
        this.command = command;
    }
}

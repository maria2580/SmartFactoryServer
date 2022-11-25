package com.primitive.SmartFactoryServer.DAO.Sensors;

import com.primitive.SmartFactoryServer.DTO.Sensor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class SensorDAO {
    @Id
    @JoinColumn(name = "sensor_index")//외래키 이름 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;
    @Column(nullable = false)
    private Long userIndex;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String command;
    public void change(Sensor sensor){
        this.name= sensor.getName();
        this.command= sensor.getCommand();
    }
    @Builder
    public SensorDAO(Long userIndex,String name, String command) {
        this.userIndex=userIndex;
        this.name = name;
        this.command = command;
    }
}

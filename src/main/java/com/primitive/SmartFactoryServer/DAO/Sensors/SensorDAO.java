package com.primitive.SmartFactoryServer.DAO.Sensors;

import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
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
    @Column(name = "sensorIndex")//외래키 이름 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;
    @ManyToOne
    @JoinColumn(name="userIndex")
    private UsersDAO user;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String command;
    public void change(Sensor sensor){
        this.name= sensor.getName();
        this.command= sensor.getCommand();
    }
    @Builder
    public SensorDAO(UsersDAO user, String name, String command) {
        this.user = user;
        this.name = name;
        this.command = command;
    }
}

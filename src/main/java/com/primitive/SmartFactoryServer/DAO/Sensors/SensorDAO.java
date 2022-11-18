package com.primitive.SmartFactoryServer.DAO.Sensors;

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
    String name;
    @Column(nullable = false)
    String command;

    @Builder
    public SensorDAO(String name, String command) {
        this.name = name;
        this.command = command;
    }
}

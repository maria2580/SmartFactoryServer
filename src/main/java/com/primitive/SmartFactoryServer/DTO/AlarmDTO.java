package com.primitive.SmartFactoryServer.DTO;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import com.primitive.SmartFactoryServer.DAO.Sensors.SensorDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;


public class AlarmDTO extends BaseTimeEntity {

    Long index; // 알람 구분 인덱스
    Long sensorIndex; // 센서 고유 인덱스
    // on/off알람일경우 ?
    double from; // 알람 최저 기준치
    double to; // 알람 최고 기준치
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public AlarmDTO(Long index, Long sensorIndex, double from, double to, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.index = index;
        this.sensorIndex = sensorIndex;
        this.from = from;
        this.to = to;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}

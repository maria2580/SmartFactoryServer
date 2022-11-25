package com.primitive.SmartFactoryServer.DTO;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlarmDTO extends BaseTimeEntity {

    private Long index; // 알람 구분 인덱스
    private  Long sensorIndex; // 센서 고유 인덱스
    // on/off알람일경우 ?
    private double minimum; // 알람 최저 기준치
    private double maximum; // 알람 최고 기준치
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public AlarmDTO(Long index, Long sensorIndex, double minimum, double maximum, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.index = index;
        this.sensorIndex = sensorIndex;
        this.minimum = minimum;
        this.maximum = maximum;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}

package com.primitive.SmartFactoryServer.DAO.Sensors;

import com.primitive.SmartFactoryServer.DAO.Alarms.AlarmDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<SensorDAO,Long> {
    List<SensorDAO> findAllByUserIndex(Long userIndex);
}

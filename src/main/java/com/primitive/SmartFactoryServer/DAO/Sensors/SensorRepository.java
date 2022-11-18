package com.primitive.SmartFactoryServer.DAO.Sensors;

import com.primitive.SmartFactoryServer.DAO.Alarms.AlarmDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<SensorDAO,Long> {
}

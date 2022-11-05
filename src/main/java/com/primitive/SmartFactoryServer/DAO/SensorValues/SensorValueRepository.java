package com.primitive.SmartFactoryServer.DAO.SensorValues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorValueRepository extends JpaRepository<SensorValueDAO,Long> {
}

package com.primitive.SmartFactoryServer.DAO.Alarms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmDAO,Long> {
}

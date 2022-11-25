package com.primitive.SmartFactoryServer.DAO.SensorValues;

import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorValueRepository extends JpaRepository<SensorValueDAO,Long> {
    List<SensorValueDAO> findAllByUser(UsersDAO usersDAO);
}

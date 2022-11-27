package com.primitive.SmartFactoryServer.DAO.Alarms;

import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmDAO,Long> {
    List<AlarmDAO> findByUser(UsersDAO user);
}

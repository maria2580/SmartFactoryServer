package com.primitive.SmartFactoryServer.DAO.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UsersDAO,Long> {
    List<UsersDAO> findByUserId(String userId);
}

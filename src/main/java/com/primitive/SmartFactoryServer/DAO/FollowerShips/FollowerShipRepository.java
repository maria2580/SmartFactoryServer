package com.primitive.SmartFactoryServer.DAO.FollowerShips;

import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerShipRepository extends JpaRepository<FollowerShipDAO,Long> {
    List<FollowerShipDAO> findByFollowUser(UsersDAO usersDAO);
    List<FollowerShipDAO> findByFollowerUser(UsersDAO usersDAO);

}

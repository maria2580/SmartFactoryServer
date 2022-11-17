package com.primitive.SmartFactoryServer.DAO.FollowerShips;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerShipRepository extends JpaRepository<FollowerShipDAO,Long> {
}

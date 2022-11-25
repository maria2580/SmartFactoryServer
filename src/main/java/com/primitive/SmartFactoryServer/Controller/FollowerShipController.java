package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DAO.FollowerShips.FollowerShipDAO;
import com.primitive.SmartFactoryServer.DAO.FollowerShips.FollowerShipRepository;
import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import com.primitive.SmartFactoryServer.VO.FollowshipVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class FollowerShipController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SensorValueRepository sensorValueRepository;
    @Autowired
    FollowerShipRepository followerShipRepository;
    @PostMapping("followership")
    public String post_follower(@RequestBody FollowshipVO followshipVO){
        UsersDAO followerUser = usersRepository.findByUserId(followshipVO.getMyID()).get(0);
        UsersDAO followUser = usersRepository.findByUserId(followshipVO.getTargetID()).get(0);

        FollowerShipDAO followerShipDAO = new FollowerShipDAO(followerUser.getIndex(), followUser.getIndex());
        followerShipRepository.save(followerShipDAO);
        return"";
    }

    @GetMapping("followership")
    public List<FollowerShipDAO> get_follower(@RequestBody String myID){
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(myID);
        UsersDAO usersDAO = usersDAOList.get(0);
        List<FollowerShipDAO> followerShipDAOS = followerShipRepository
                .findAllByFollowUserIndex(usersDAO.getIndex());


        return followerShipDAOS;
    }
    @PatchMapping("followership")
    public String patch_follower(@RequestBody Long followerShipIndex,@RequestParam(value = "enable") boolean enable){
        FollowerShipDAO followerShipDAO = followerShipRepository.findById(followerShipIndex).get();
        if(enable){
            followerShipDAO.followAccept();
            followerShipRepository.save(followerShipDAO);
        }else{
            followerShipDAO.followDisable();
            followerShipRepository.save(followerShipDAO);
        }
        return "";
    }
}

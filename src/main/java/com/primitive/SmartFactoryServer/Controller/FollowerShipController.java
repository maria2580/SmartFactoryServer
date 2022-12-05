package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DAO.FollowerShips.FollowerShipDAO;
import com.primitive.SmartFactoryServer.DAO.FollowerShips.FollowerShipRepository;
import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import com.primitive.SmartFactoryServer.DTO.FollowerShipDTO;
import com.primitive.SmartFactoryServer.VO.FollowshipVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        FollowerShipDAO followerShipDAO = new FollowerShipDAO(followerUser, followUser);
        followerShipRepository.save(followerShipDAO);
        return"";
    }
    @GetMapping("followership/{ID}/follow")
    public List<FollowerShipDTO> get_following(@PathVariable("ID") String myID){
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(myID);
        UsersDAO usersDAO = usersDAOList.get(0);
        List<FollowerShipDAO> followerShipDAOS = followerShipRepository
                .findByFollowerUser(usersDAO);//내가 팔로우 하는 행 의 리스트
        List<FollowerShipDTO> followDTOList = new ArrayList<>();
        for (FollowerShipDAO fdao : followerShipDAOS) {
            followDTOList.add(new FollowerShipDTO(fdao.getIndex(),fdao.getFollowerUser().getIndex(),fdao.getFollowUser().getIndex(),fdao.isEnable(),fdao.getCreatedDate(),fdao.getModifiedDate()));
            System.out.println("followership/{ID}/follow"+fdao.getFollowerUser().getIndex()+"   ->    "+fdao.getFollowUser().getIndex());
        }
        return followDTOList;
    }

    @GetMapping("followership/{ID}/follower")
    public List<FollowerShipDTO> get_follower(@PathVariable("ID") String myID){
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(myID);
        UsersDAO usersDAO = usersDAOList.get(0);
        List<FollowerShipDAO> followerShipDAOS = followerShipRepository
                .findByFollowUser(usersDAO);//내가 팔로우당하는 행의 리스트
        List<FollowerShipDTO> followerDTOList = new ArrayList<>();
        for (FollowerShipDAO fdao : followerShipDAOS) {
            followerDTOList.add(new FollowerShipDTO(fdao.getIndex(),fdao.getFollowerUser().getIndex(),fdao.getFollowUser().getIndex(),fdao.isEnable(),fdao.getCreatedDate(),fdao.getModifiedDate()));
            System.out.println("followership/{ID}/follower"+fdao.getFollowerUser().getIndex()+"   ->    "+fdao.getFollowUser().getIndex());
        }
        return followerDTOList;
    }
    @PatchMapping("followership/{index}")
    public String patch_follower(@PathVariable("index") Long followerShipIndex,@RequestParam(value = "enable") boolean enable){
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
    @DeleteMapping("followership/{ID}")
    public String delete_follower(@PathVariable("ID") Long followerShipIndex){
        FollowerShipDAO followerShipDAO = followerShipRepository.findById(followerShipIndex).get();
        followerShipRepository.delete(followerShipDAO);
        return "";
    }
}

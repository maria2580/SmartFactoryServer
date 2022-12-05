package com.primitive.SmartFactoryServer.Controller;


import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import com.primitive.SmartFactoryServer.VO.LoginVO;
import com.primitive.SmartFactoryServer.VO.SignUpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class User_Controller {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SensorValueRepository sensorValueRepository;


    @PostMapping("sign_up")
    public String post_signUp_request(@RequestBody SignUpVO idpw){
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(idpw.ID);
        boolean flag=false;
        for(int i=0;i<usersDAOList.size();i++){
            flag= true;
        }//flag가 true라면 이미 존재하는 유저임
        if (flag){
            return LoginStatus.EXIST_ID+"";
        }
        else {
            UsersDAO usersDAO=new UsersDAO(idpw.ID, idpw.PW);
            usersRepository.save(usersDAO);
        }
        return LoginStatus.PERMITTED+"";
    }

    @PostMapping("login")
    public String login_request(@RequestBody LoginVO idpw) {
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(idpw.ID);
        boolean flag=false;
        for(int i=0;i<usersDAOList.size();i++){
            flag=true;
        }//flag가 true라면 존재하는 유저1
        if(flag&&usersDAOList.get(0).getPw().equals(idpw.PW)){
            if(idpw.env==Env.FACTORY){
                return LoginStatus.PERMITTED+"";

            } else if (idpw.env==Env.CLIENT) {
                return LoginStatus.PERMITTED+"";
            }
            else {
                return LoginStatus.DENIED+"";
            }
        }
        else {
            return LoginStatus.DENIED+"";
        }
    }
    @GetMapping("User_list/{partOfID}")
    public List<String> get_list_of_user_by(@PathVariable("partOfID")String partOfID){
        List<UsersDAO> usersDAOList=usersRepository.findAll();
        List<String> result=new ArrayList<>();
        for (UsersDAO user:usersDAOList) {
            if(user.getUserId().contains(partOfID)){
                result.add(user.getUserId());
            }
        }
        return result;
    }
    @GetMapping("user/{index}")
    public String get_userID_of_by_index(@PathVariable("index")long index){
        UsersDAO usersDAOt=usersRepository.findById(index).get();
        return usersDAOt.getUserId();
    }



}
class LoginStatus{
    public static final int PERMITTED=0;
    public static final int DENIED=1;
    public static final int EXIST_ID=2;
}
class Env{
    public static final int FACTORY=0;
    public static final int CLIENT=1;
}
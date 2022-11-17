package com.primitive.SmartFactoryServer.Controller;


import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class Login_Controller {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SensorValueRepository sensorValueRepository;


    @PostMapping("sign_up")
    public String post_signUp_request(@RequestBody String ID, @RequestBody String PW){
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(ID);
        boolean flag=false;
        for(int i=0;i<usersDAOList.size();i++){
            flag= true;
        }//flag가 true라면 이미 존재하는 유저임
        if (flag){
            return "sign_up_failed";
        }
        else {
            UsersDAO usersDAO=new UsersDAO(ID,PW);
            usersRepository.save(usersDAO);
        }

        return "sign_up_successful";
    }

    @PostMapping("login")
    public String login_request(@RequestBody String ID, @RequestBody String PW, @RequestParam String env) {
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(ID);
        boolean flag=false;
        for(int i=0;i<usersDAOList.size();i++){
            flag=true;
        }//flag가 true라면 존재하는 유저
        if(flag&&usersDAOList.get(0).getPw().equals(PW)){
            if(env.equals("Factory")){
                usersDAOList.get(0).updateFactoryToken();
                return usersDAOList.get(0).getFactoryToken();
            } else if (env.equals("Client")) {
                usersDAOList.get(0).updateClientToken();
                return usersDAOList.get(0).getClientToken();
            }
            else {
                return "invalid Parameter in env";
            }
        }
        else {
            return "invalid user or pw";
        }
    }
}

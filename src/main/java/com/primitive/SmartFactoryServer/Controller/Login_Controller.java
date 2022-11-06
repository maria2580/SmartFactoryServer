package com.primitive.SmartFactoryServer.Controller;


import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class Login_Controller {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SensorValueRepository sensorValueRepository;


    @PostMapping("sign_up")
    public String post_signUp_request(@RequestBody String ID, @RequestBody String PW){

        return "";
    }

    @PostMapping("login")
    public String login_request(@RequestBody String ID, @RequestBody String PW) {

        return "working2";
    }
}

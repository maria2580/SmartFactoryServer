package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class PushTokenController {
    @Autowired
    UsersRepository usersRepository;

    @PostMapping("pushToken/{ID}")
    public String postPushToken(@RequestBody String pushToken, @PathVariable("ID") String id){
        UsersDAO usersDAO = usersRepository.findByUserId(id).get(0);
        usersDAO.updatePushToken(pushToken);
        usersRepository.save(usersDAO);
        return "";
    }

}

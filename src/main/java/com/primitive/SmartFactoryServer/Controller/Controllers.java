package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueDAO;
import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import com.primitive.SmartFactoryServer.DTO.SensorValueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class Controllers {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SensorValueRepository sensorValueRepository;
    @PostMapping("sensors")
    public String post_sensor_value(@RequestBody SensorValueDTO[] sensorValueDTOs, @RequestBody String ID, @RequestBody String loginToken){
        JSONObject jsonObject = new JSONObject();
        JSONArray sensorValuesArr = new JSONArray();
        sensorValuesArr.put(sensorValueDTOs[0]);
        String sensorValuesString = sensorValuesArr.toString();

        UsersDAO findedUser= usersRepository.findByUserId(ID).get(0);
        Long userIndex = findedUser.getIndex();

        SensorValueDAO sensorValue= SensorValueDAO.builder()
                .sensorValues(sensorValuesString)
                .userIndex(userIndex)
                .build();
        sensorValueRepository.save(sensorValue);
        return "";
    }
    @PostMapping("login")
    public String post_login_request(@RequestBody String ID, @RequestBody String PW){

        return "";
    }

    @PostMapping("sign_up")
    public String post_signUp_request(@RequestBody String ID, @RequestBody String PW){

        return "";
    }
    @GetMapping("sensors/all")
    public String get_sensor_value_all(){
        return "";
    }
    @GetMapping("sensors/hour")
    public String get_sensor_value_hour(){
        return "";
    }
    @GetMapping ("sensors/resent_one")
    public String get_sensor_value_resent_one(){
        return "";
    }
}

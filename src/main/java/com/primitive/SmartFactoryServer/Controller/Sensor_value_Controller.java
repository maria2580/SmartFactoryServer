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

import java.util.ArrayList;

@RestController
@RequestMapping("")
public class Sensor_value_Controller {

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


        SensorValueDAO sensorValue= SensorValueDAO.builder()
                .sensorValues(sensorValuesString)
                .user(findedUser)
                .build();
        sensorValueRepository.save(sensorValue);
        return "";
    }


    @GetMapping("sensors/{ID}/resent_one")
    public SensorValueDTO[] get_sensor_value_resent_one(@RequestParam String ID, @RequestBody String token){
        ArrayList<SensorValueDTO> sensorValueses= new ArrayList<>();


        return (SensorValueDTO[])sensorValueses.toArray();
    }
    @GetMapping("sensors/{ID}")
    public SensorValueDTO[][] get_sensor_value_by_period(@RequestParam String ID, @RequestBody String token, @PathVariable String from, @PathVariable String to){
        ArrayList<SensorValueDTO> sensorValues_one= new ArrayList<>();
        ArrayList<SensorValueDTO[]> sensorValues_ten= new ArrayList<>();//이 어레이에 추가할 때는 one을 toArray()하여 add()호출


        return (SensorValueDTO[][])sensorValues_ten.toArray();
    }
    @GetMapping ("sensors/{ID}/all")
    public SensorValueDTO[][] get_sensor_value_all(@RequestParam String ID, @RequestBody String token){
        ArrayList<SensorValueDTO> sensorValues_one= new ArrayList<>();
        ArrayList<SensorValueDTO[]> sensorValues_ten= new ArrayList<>();//이 어레이에 추가할 때는 one을 toArray()하여 add()호출


        return (SensorValueDTO[][])sensorValues_ten.toArray();
    }

}

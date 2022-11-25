package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DAO.Sensors.SensorDAO;
import com.primitive.SmartFactoryServer.DAO.Sensors.SensorRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import com.primitive.SmartFactoryServer.DTO.Sensor;
import com.primitive.SmartFactoryServer.DTO.SensorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sensors")
public class SensorController {
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    UsersRepository usersRepository;
   @PostMapping
   public void post_new_sensor(@RequestBody SensorDTO sensorDTO){
       UsersDAO usersDAO =usersRepository.findByUserId(sensorDTO.getUser_id()).get(0);
       SensorDAO createdSensor = SensorDAO.builder()
               .userIndex(usersDAO.getIndex())
               .name(sensorDTO.getName())
               .command(sensorDTO.getCommand())
               .build();
       sensorRepository.save(createdSensor);
   }
   @GetMapping
   public List<SensorDAO> get_all_sensor(@RequestBody String id){
       UsersDAO usersDAO =usersRepository.findByUserId(id).get(0);
       List<SensorDAO> sensorDAOs= sensorRepository.findAllByUserIndex(usersDAO.getIndex());
       return sensorDAOs;
   }
   @PatchMapping
   public void update_sensor(@RequestBody Sensor sensor){
       SensorDAO sensorDAO = sensorRepository.findById(sensor.getIndex()).get();
       sensorDAO.change(sensor);
       sensorRepository.save(sensorDAO);
   }
   @DeleteMapping
   public void delete_sensor(@RequestBody long index){
       SensorDAO sensorDAO = sensorRepository.findById(index).get();
       sensorRepository.delete(sensorDAO);

   }

}

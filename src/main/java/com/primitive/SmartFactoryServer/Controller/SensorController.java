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
public class SensorController {
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    UsersRepository usersRepository;
   @PostMapping("sensors")
   public void post_new_sensor(@RequestBody SensorDTO sensorDTO){
       UsersDAO usersDAO =usersRepository.findByUserId(sensorDTO.getUser_id()).get(0);
       System.out.println("sensorDTO값 도착확인: 센서명: "+sensorDTO.getName()+" command: "+sensorDTO.getCommand()+"  userid: "+sensorDTO.getUser_id());

       SensorDAO createdSensor = SensorDAO.builder()
               .user(usersDAO)
               .name(sensorDTO.getName())
               .command(sensorDTO.getCommand())
               .build();
       sensorRepository.save(createdSensor);
       System.out.println("sensor값 도착확인: 센서명: "+createdSensor.getName()+" command: "+createdSensor.getCommand()+"  index: "+createdSensor.getIndex());
   }
   @GetMapping("sensors/{ID}")
   public List<SensorDAO> get_all_sensor(@PathVariable("ID") String id){
       System.out.println("받은값 "+id);
       System.out.println("받은값 "+id);
       UsersDAO usersDAO =usersRepository.findByUserId(id).get(0);
       List<SensorDAO> sensorDAOs= sensorRepository.findByUser(usersDAO);
       return sensorDAOs;
   }
   @PatchMapping("sensors")
   public void update_sensor(@RequestBody Sensor sensor){
       SensorDAO sensorDAO = sensorRepository.findById(sensor.getIndex()).get();
       sensorDAO.change(sensor);
       sensorRepository.save(sensorDAO);
   }
   @DeleteMapping("sensors/{index}")
   public void delete_sensor(@PathVariable("index") long index){
       SensorDAO sensorDAO = sensorRepository.findById(index).get();
       sensorRepository.delete(sensorDAO);

   }

}

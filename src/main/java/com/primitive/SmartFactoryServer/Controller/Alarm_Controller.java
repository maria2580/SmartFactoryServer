package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DAO.Alarms.AlarmDAO;
import com.primitive.SmartFactoryServer.DAO.Alarms.AlarmRepository;
import com.primitive.SmartFactoryServer.DAO.Sensors.SensorDAO;
import com.primitive.SmartFactoryServer.DAO.Sensors.SensorRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import com.primitive.SmartFactoryServer.DTO.AlarmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("alarm")
public class Alarm_Controller {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    AlarmRepository alarmRepository;
    @PostMapping("alarm/{ID}")//알람 항목추가
    public String post_alarm(@PathVariable("ID") String myID, @RequestBody AlarmDTO alarmDTO){
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(myID);
        UsersDAO usersDAO = usersDAOList.get(0);
        SensorDAO sensorDAO = sensorRepository.findById(alarmDTO.getSensorIndex()).get();
        AlarmDAO alarmDAO = new AlarmDAO(usersDAO, sensorDAO,alarmDTO.getMinimum(), alarmDTO.getMaximum());
        return "";
    }

    @GetMapping("alarm/{ID}")//알람리스트 호출
    public List<AlarmDAO> get_alarm(@PathVariable("ID")String myID){
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(myID);
        UsersDAO usersDAO = usersDAOList.get(0);
        List<AlarmDAO> alarmDAOS = alarmRepository.findByUser(usersDAO);
        return alarmDAOS;
    }

    @PatchMapping("alarm")//센서 알람 기준치 설정
    public String patch_from_alarm(@RequestBody AlarmDTO alarmDTO){
        AlarmDAO alarmDAO = alarmRepository.findById(alarmDTO.getIndex()).get();
        alarmDAO.updateMinimum(alarmDTO.getMinimum());
        alarmDAO.updateMaximum(alarmDTO.getMaximum());

        alarmRepository.save(alarmDAO);

        return "";
    }

    @DeleteMapping("alarm/{index}")//알람 항목 삭제
    public String delete_alarm(@PathVariable("index") Long alarmIndex){
        SensorDAO sensorDAO = sensorRepository.findById(alarmIndex).get();
        sensorRepository.delete(sensorDAO);

        return "";
    }





}

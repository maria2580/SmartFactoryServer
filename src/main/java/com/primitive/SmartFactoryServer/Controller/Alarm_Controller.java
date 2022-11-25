package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DAO.Alarms.AlarmDAO;
import com.primitive.SmartFactoryServer.DAO.Alarms.AlarmRepository;
import com.primitive.SmartFactoryServer.DAO.Sensors.SensorDAO;
import com.primitive.SmartFactoryServer.DAO.Sensors.SensorRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
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
    @PostMapping("alarm")//알람 항목추가
    public String post_alarm(@RequestBody String myID,@RequestBody Long sensorIndex,  @RequestBody double from, @RequestBody double to){
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(myID);
        UsersDAO usersDAO = usersDAOList.get(0);
        SensorDAO sensorDAO = sensorRepository.findById(sensorIndex).get();
        AlarmDAO alarmDAO = new AlarmDAO(usersDAO, sensorDAO,from, to);
        return "";
    }

    @GetMapping("alarm")//알람리스트 호출
    public List<AlarmDAO> get_alarm(@RequestBody String myID){
        List<UsersDAO> usersDAOList = usersRepository.findByUserId(myID);
        UsersDAO usersDAO = usersDAOList.get(0);
        List<AlarmDAO> alarmDAOS = alarmRepository.findAllByUserIndex(usersDAO.getIndex());
        return alarmDAOS;
    }

    @PatchMapping("alarm")//센서 알람 기준치 설정
    public String patch_from_alarm(@RequestBody Long alarmIndex, @RequestBody double from, @RequestBody double to){
        AlarmDAO alarmDAO = alarmRepository.findById(alarmIndex).get();
        alarmDAO.updateMinimum(from);
        alarmDAO.updateMaximum(to);

        alarmRepository.save(alarmDAO);

        return "";
    }

    @DeleteMapping("alarm")//알람 항목 삭제
    public String delete_alarm(@RequestBody Long alarmIndex){
        SensorDAO sensorDAO = sensorRepository.findById(alarmIndex).get();
        sensorRepository.delete(sensorDAO);

        return "";
    }





}

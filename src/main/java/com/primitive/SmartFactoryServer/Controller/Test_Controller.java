package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DAO.Alarms.AlarmRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import com.primitive.SmartFactoryServer.FCMessage;
import com.primitive.SmartFactoryServer.SensorObserverThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class Test_Controller {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    AlarmRepository alarmRepository;
    @Autowired
    Sensor_value_Controller sensor_value_controller;
    @Autowired
    SensorObserverThread t;

    int init=0;

    @GetMapping("observer/{index}")
    public String testController(@PathVariable String index){
        if(index.equals("0")){
            if (init==0){
                t= new SensorObserverThread(usersRepository,alarmRepository,sensor_value_controller);
                t.run();
                init++;
            }
            return "working"+index;
        }
        else if (index.equals("1")){
            List<UsersDAO> list=usersRepository.findAll();
            System.out.println("전송대기");

            for (int i = 0; i < list.size(); i++) {
                try {
                    System.out.println("전송중->"+list.get(i).getUserId()+" "+list.size());
                    FCMessage.send("테스트 전송", "테스트 전송이 실행되었습니다."+System.currentTimeMillis(), list.get(i).getPushToken());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return "working"+index;
        }
        return "wo"+index;
    }



}

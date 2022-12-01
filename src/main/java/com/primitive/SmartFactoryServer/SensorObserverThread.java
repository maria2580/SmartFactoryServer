package com.primitive.SmartFactoryServer;

import com.primitive.SmartFactoryServer.Controller.Sensor_value_Controller;
import com.primitive.SmartFactoryServer.DAO.Alarms.AlarmDAO;
import com.primitive.SmartFactoryServer.DAO.Alarms.AlarmRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import com.primitive.SmartFactoryServer.DTO.SensorValue;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.*;


@Controller
public class SensorObserverThread{
    UsersRepository usersRepository;
    AlarmRepository alarmRepository;
    Sensor_value_Controller sensor_value_controller;

    public SensorObserverThread(UsersRepository usersRepository, AlarmRepository alarmRepository, Sensor_value_Controller sensor_value_controller) {
        this.usersRepository = usersRepository;
        this.alarmRepository = alarmRepository;
        this.sensor_value_controller = sensor_value_controller;
    }

    private SensorValue findBySensorName(String name, SensorValue[] sensorValues){
        for (int i = 0; i <sensorValues.length; i++) {
            if (sensorValues[i].getName().equals(name)){
                return sensorValues[i];
            }
        }
        return new SensorValue("","");
    }

    @Async
    public void run() {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,10,30, TimeUnit.SECONDS,blockingQueue);
        while (true) {
            List<UsersDAO> usersDAOList = usersRepository.findAll();
            for (int i = 0; i < usersDAOList.size(); i++) {
                System.out.println(usersDAOList.get(i).getUserId()+" 토큰값 "+usersDAOList.get(i).getPushToken());
                //i는 모든 유저에 대한 인덱스.
                UsersDAO targetUser = usersDAOList.get(i);
                //해당 유저의 알람도 가져와서
                List<AlarmDAO> alarmDAOS = alarmRepository.findByUser(targetUser);
                SensorValue[] sensorValues = sensor_value_controller.get_sensor_value_resent_one(targetUser.getUserId());
                //i번째 유저 가장 최근 센서값 센서별 한개씩 가져옴
                for (int j = 0; j < alarmDAOS.size(); j++) {
                    //알람 객체 하나씩
                    AlarmDAO alarm = alarmDAOS.get(j);
                    Runnable runnable = new JobRunnable(targetUser,alarm,sensorValues);
                    threadPoolExecutor.execute(runnable);
                }
            }//모든 유저에 대한 작업 요청 끝남
            try {
                Thread.sleep(15000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
    class JobRunnable implements Runnable{
        UsersDAO targetUser;
        AlarmDAO alarm;
        SensorValue[] sensorValues;
        JobRunnable(UsersDAO targetUser, AlarmDAO alarm, SensorValue[] sensorValues){
            this.targetUser=targetUser;
            this.alarm=alarm;
            this.sensorValues=sensorValues;
        }
        @Override
        public void run() {
            SensorValue sensorValue = findBySensorName(alarm.getSensor().getName(),sensorValues);
            System.out.println("검사중인 센서 값 : "+sensorValue);
            //알람 객체에 있는 센서값
            double value = Double.parseDouble(sensorValue.getValue());
            if (!(alarm.getMinimum() <= value & value <= alarm.getMaximum())) {
                String pushToken = targetUser.getPushToken();
                try {
                    System.out.println("위험 알람 전송"+ " 센서:" + sensorValue.getName() + "에서 이상신호가 감지되었습니다.");
                    FCMessage.send("위험알람", "유저: "+ targetUser.getUserId() +" 센서:" + sensorValue.getName() + "에서 이상신호가 감지되었습니다.", pushToken);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
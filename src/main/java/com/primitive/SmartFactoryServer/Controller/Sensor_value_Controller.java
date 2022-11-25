package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueDAO;
import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import com.primitive.SmartFactoryServer.DTO.SensorValueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("")
public class Sensor_value_Controller {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SensorValueRepository sensorValueRepository;
    @PostMapping("sensors_value")
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


    @GetMapping("sensors_value/{ID}/resent_one")
    public SensorValueDTO[] get_sensor_value_resent_one(@PathVariable("ID") String ID, @RequestBody String token) throws JSONException {
        ArrayList<SensorValueDTO> sensorValues= new ArrayList<>();
        List<SensorValueDAO> sensorValueDTOList= sensorValueRepository.findAllByUser(usersRepository.findByUserId(ID).get(0));

        JSONArray jsonArray= new JSONArray(sensorValueDTOList.get(sensorValueDTOList.size()-1).getSensorValues());
        for(int i=0;i<sensorValueDTOList.size();i++){
            sensorValues.add((SensorValueDTO)jsonArray.get(i));
        }

        return (SensorValueDTO[])sensorValues.toArray();
    }
    @GetMapping("sensors_value/{ID}")
    public SensorValueDTO[][] get_sensor_value_by_period(@PathVariable("ID") String ID, @RequestBody String token, @RequestParam("from") String from, @RequestParam("to") String to){
        ArrayList<ArrayList<SensorValueDTO>> result=new ArrayList<>();
        List<SensorValueDAO> sensorValueDTOList= sensorValueRepository.findAllByUser(usersRepository.findByUserId(ID).get(0));
        JSONArray[] jsonArray=new JSONArray[sensorValueDTOList.size()];
        try {
            for(int i=0;i<sensorValueDTOList.size();i++){
                jsonArray[i]= new JSONArray(sensorValueDTOList.get(i).getSensorValues());
            }
            for (int i=0;i<jsonArray.length;i++){
                //i=0에 같은 시점 센서값 n개 만큼 반복
                for(int j=0; j<jsonArray[i].length();j++){
                    result.get(i).add((SensorValueDTO)jsonArray[i].get(j));
                }
            }
        }catch (JSONException e){e.printStackTrace();}
        ;//안드로이드에서 날짜 받아오는 함수 사용할 예정

        ArrayList<ArrayList<SensorValueDTO>> finedResult=new ArrayList<>();
        Calendar min=getCaleder(from);
        Calendar max=getCaleder(to);
        for(int i=0; i<result.size();i++) {
            LocalDateTime time = result.get(i).get(0).getCreatedDate();
            Calendar temp = new GregorianCalendar();
            temp.set(time.getYear(), time.getMonthValue(), time.getDayOfMonth());
            if (temp.after(min) & temp.before(max)) {
                finedResult.add(result.get(i));
            }
        }
        return (SensorValueDTO[][])finedResult.toArray();
    }
    @GetMapping ("sensors_value/{ID}/all")
    public SensorValueDTO[][] get_sensor_value_all(@RequestParam String ID, @RequestBody String token)  {
        ArrayList<ArrayList<SensorValueDTO>> result=new ArrayList<>();
        List<SensorValueDAO> sensorValueDTOList= sensorValueRepository.findAllByUser(usersRepository.findByUserId(ID).get(0));
        JSONArray[] jsonArray=new JSONArray[sensorValueDTOList.size()];
        try {
            for(int i=0;i<sensorValueDTOList.size();i++){
                jsonArray[i]= new JSONArray(sensorValueDTOList.get(i).getSensorValues());
            }
            for (int i=0;i<jsonArray.length;i++){
                //i=0에 같은 시점 센서값 n개 만큼 반복
                for(int j=0; j<jsonArray[i].length();j++){
                    result.get(i).add((SensorValueDTO)jsonArray[i].get(j));
                }
            }
        }catch (JSONException e){e.printStackTrace();}

        return (SensorValueDTO[][])result.toArray();
    }
    static Calendar getCaleder(String yyyymmdd){
        int Year=Integer.parseInt(yyyymmdd)/10000;
        int Month=Integer.parseInt(yyyymmdd)%(Year*10000);
        int Day=Integer.parseInt(yyyymmdd)%(Year*10000+Month*100);
        return new GregorianCalendar(Year,Month,Day);
    }
}

package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueDAO;
import com.primitive.SmartFactoryServer.DAO.SensorValues.SensorValueRepository;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import com.primitive.SmartFactoryServer.DAO.users.UsersRepository;
import com.primitive.SmartFactoryServer.DTO.SensorValue;
import com.primitive.SmartFactoryServer.DTO.SensorValueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping
public class Sensor_value_Controller {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SensorValueRepository sensorValueRepository;
    @PostMapping("sensors_value")
    public String post_sensor_value(@RequestBody SensorValueDTO sensorValueDTO){
        String sensorValuesString="";

        for(int i = 0 ; i< sensorValueDTO.getSensorValues().length;i++){
            System.out.println("받은 값:"+sensorValueDTO.getSensorValues()[i]);
        }
        UsersDAO findedUser= usersRepository.findByUserId(sensorValueDTO.getID()).get(0);
        SensorValueDAO sensorValue= new SensorValueDAO(findedUser,sensorValueDTO.valueToString());
        sensorValueRepository.save(sensorValue);
        return "";
    }


    @GetMapping("sensors_value/{ID}/resent_one")
    public SensorValue[] get_sensor_value_resent_one(@PathVariable("ID") String ID) {
        List<SensorValueDAO> sensorValueDAOList= sensorValueRepository.findByUser(usersRepository.findByUserId(ID).get(0));
        int lastIndex=sensorValueDAOList.size()-1;
        SensorValue[] result = new SensorValue[0];
        JSONArray jsonArrays=new JSONArray();
        try {
            jsonArrays=new JSONArray(sensorValueDAOList.get(lastIndex).getSensorValues());
            result=new SensorValue[jsonArrays.length()];
            for(int i=0;i<jsonArrays.length();i++){
                JSONObject jsonObject= new JSONObject(jsonArrays.get(i).toString());
                result[i]=new SensorValue(jsonObject.getString("name"), jsonObject.getString("value")) ;
            }
        }catch (Exception e){e.printStackTrace();}

        return result;
    }
    @GetMapping("sensors_value/{ID}")
    public SensorValue[][] get_sensor_value_by_period(@PathVariable("ID") String ID, @RequestParam("from") String from, @RequestParam("to") String to){
        List<SensorValueDAO> sensorValueDAOList= sensorValueRepository.findByUser(usersRepository.findByUserId(ID).get(0));
        SensorValue[][] result=new SensorValue[sensorValueDAOList.size()][];
        JSONArray[] jsonArrays=new JSONArray[sensorValueDAOList.size()];
        try {
            for(int i=0;i<sensorValueDAOList.size();i++){
                jsonArrays[i]= new JSONArray(sensorValueDAOList.get(i).getSensorValues());
                result[i]=new SensorValue[jsonArrays[i].length()];
                for (int j = 0 ; j < jsonArrays[i].length();j++){
                    JSONObject jsonObject= new JSONObject(jsonArrays[i].get(j).toString());
                    result[i][j]=new SensorValue(jsonObject.getString("name"), jsonObject.getString("value")) ;
                }
            }
        }catch (Exception e){e.printStackTrace();}

        Calendar min=getCalender(from);
        Calendar max=getCalender(to);

        ArrayList<SensorValue[]> finedResult=new ArrayList<>();
        for(int i=0; i<sensorValueDAOList.size();i++) {
            LocalDateTime time = sensorValueDAOList.get(i).getCreatedDate();
            Calendar temp=new GregorianCalendar(time.getYear(),time.getMonthValue(),time.getDayOfMonth(),time.getHour(),time.getMinute(),time.getSecond());
            if (temp.after(min) & temp.before(max)) {
                finedResult.add(result[i]);
            }
        }
        SensorValue[][] sensorValues = new SensorValue[finedResult.size()][];
        for(int i=0;i< finedResult.size();i++){
            jsonArrays[i]= new JSONArray(finedResult.get(i));
            sensorValues[i]=new SensorValue[finedResult.get(i).length];
            for (int j = 0 ; j < finedResult.get(i).length;j++){
                JSONObject jsonObject= new JSONObject(jsonArrays[i].get(j).toString());
                sensorValues[i][j]=new SensorValue(jsonObject.getString("name"), jsonObject.getString("value")) ;
            }

        }
        return sensorValues;
    }
    @GetMapping ("sensors_value/{ID}/all")
    public SensorValue[][] get_sensor_value_all(@PathVariable("ID") String ID)  {
        List<SensorValueDAO> sensorValueDAOList= sensorValueRepository.findByUser(usersRepository.findByUserId(ID).get(0));
        SensorValue[][] result=new SensorValue[sensorValueDAOList.size()][];
        JSONArray[] jsonArrays=new JSONArray[sensorValueDAOList.size()];
        try {
            for(int i=0;i<sensorValueDAOList.size();i++){
                jsonArrays[i]= new JSONArray(sensorValueDAOList.get(i).getSensorValues());
                result[i]=new SensorValue[jsonArrays[i].length()];
                for (int j = 0 ; j < jsonArrays[i].length();j++){
                    JSONObject jsonObject= new JSONObject(jsonArrays[i].get(j).toString());
                    result[i][j]=new SensorValue(jsonObject.getString("name"), jsonObject.getString("value")) ;
                }
            }
        }catch (Exception e){e.printStackTrace();}

        return result;
    }
    static Calendar getCalender(String yyyymmddhhmmss){
        long temp = Long.parseLong(yyyymmddhhmmss);
        int Year=(int) (temp/10000000000L);
        int Month=(int) (temp%(Year*10000000000L))/100000000;
        int Day=(int) (temp%(Year*10000000000L+Month*100000000))/1000000;
        int hrs=(int) (temp%(Year*10000000000L+Month*100000000+Day*1000000))/10000;
        int min=(int) (temp%(Year*10000000000L+Month*100000000+Day*1000000+hrs*10000))/100;
        int sec=(int) (temp%(Year*10000000000L+Month*100000000+Day*1000000+hrs*10000+min*100));
        return new GregorianCalendar(Year,Month,Day,hrs,min,sec);
    }
}

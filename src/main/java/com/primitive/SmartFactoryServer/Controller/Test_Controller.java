package com.primitive.SmartFactoryServer.Controller;

import com.primitive.rentable_DB_api.Data_object.Items_DAO;
import com.primitive.rentable_DB_api.Data_object.User_DTO;
import com.primitive.rentable_DB_api.Data_object.Users_DAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

@RestController
@RequestMapping("")
public class Test_Controller {


    private final DB_Connection_Data key = DB_Connection_Data.getInstance();
    @GetMapping
    public String testController(){

        return "working";
    }
}

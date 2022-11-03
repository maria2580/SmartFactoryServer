package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DB_Connection_Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
public class Test_Controller {
    private final DB_Connection_Data key = DB_Connection_Data.getInstance();
    @GetMapping
    public String testController(){
        return "working";
    }
}

package com.primitive.SmartFactoryServer.Controller;

import com.primitive.SmartFactoryServer.DB_Connection_Data;
import org.springframework.web.bind.annotation.*;


@RestController
public class Test_Controller {
    private final DB_Connection_Data key = DB_Connection_Data.getInstance();
    @GetMapping("board/{index}")
    public String testController(@PathVariable String index){

        return "working"+index;
    }

}

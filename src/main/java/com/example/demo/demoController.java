package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class demoController {


    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    public String demo(@RequestParam String name){
        System.out.println(name);
        return name;
    }
}

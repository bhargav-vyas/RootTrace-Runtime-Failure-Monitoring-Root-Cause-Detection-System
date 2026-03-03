package com.bhargav.roottrace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class DemoController {
    @GetMapping("/")
    public String home(){
        return "RootTrace is running";
    }
    @GetMapping("/null-error")
    public String nullError(){
        String value = null;
        return  value.toString();
    }
    @GetMapping("/divide-error")
    public int divideError(){
         return 10/0;
    }


}

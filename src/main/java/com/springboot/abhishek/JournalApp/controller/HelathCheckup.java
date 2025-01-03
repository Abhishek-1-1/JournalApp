package com.springboot.abhishek.JournalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelathCheckup {

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }
}

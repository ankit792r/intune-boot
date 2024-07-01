package com.lmptech.intune.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("common")
public class CommonController {

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }
}

package com.example.h2example.controller;

import com.example.h2example.service.LiveTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiveTestController {

    @Autowired
    private LiveTestService liveTestService;

    @GetMapping("/")
    public @ResponseBody String liveTest() {
        return liveTestService.liveTest();
    }
}

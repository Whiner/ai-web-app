package org.donntu.ai.backend.controller;

import org.donntu.ai.backend.service.DiagnosesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final DiagnosesService diagnosesService;

    @Autowired
    public HomeController(DiagnosesService diagnosesService) {
        this.diagnosesService = diagnosesService;
    }

    @GetMapping("/test")
    public String test(){
        diagnosesService.test();
        return "Опа здарова тебе с сервачка, братан";
    }
}

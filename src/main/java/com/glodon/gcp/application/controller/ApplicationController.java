package com.glodon.gcp.application.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kinnylee
 */
@RestController("/application")
public class ApplicationController {

    @Value("${spring.application.name}")
    String app;

    @Value("${version}")
    String version;

    @Value("${env}")
    String env;

    @GetMapping("/")
    public Map<String, String> index() {
        Map<String, String> info = new HashMap<>();
        info.put("env", env);
        info.put("app", app);
        info.put("version", version);
        info.put("data", "Index, Welcome to gcp application");
        return info;
    }

    @GetMapping("/health")
    public String health(){
        return "ok";
    }

    @GetMapping("/version")
    public String version() {
        return version;
    }
}

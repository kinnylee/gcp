package com.glodon.gcp.application.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kinnylee
 */
@RestController("/application")
public class ApplicationController {

    @Value ("${version}")
    String version;

    @GetMapping("/")
    public String index() {
        return "Welcome to gcp application";
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

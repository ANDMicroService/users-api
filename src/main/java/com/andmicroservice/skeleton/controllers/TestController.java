package com.andmicroservice.skeleton.controllers;

import com.andmicroservice.skeleton.SkeletonApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        logger.info("RestController is up and running...");

        return "RestController is up and running!";
    }
}

package com.defers.crm.customers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping(produces = "application/json")
    public ResponseEntity<String> getMsg() {
        return new ResponseEntity<>("Hello!", HttpStatus.OK);
    }
}

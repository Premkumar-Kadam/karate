package com.ama.karate.controller.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.interfaceService.MasterInterfaceService;

@RestController
public class master {

    @Autowired MasterInterfaceService mis;

    // @PostMapping("/classes-list")
    // public ResponseEntity<Map<String, Object>> classesList() {

    //     mis.classList(phoneNo);
    //     ClassesDto[] response =  mis.classList(phoneNo);
    //     return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    // }

}

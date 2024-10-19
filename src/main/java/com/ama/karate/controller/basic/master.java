package com.ama.karate.controller.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.interfaceService.MasterInterfaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@RestController
public class master {

    @Autowired MasterInterfaceService mis;

    @Autowired ObjectMapper om;

    @PostMapping("/classes-list")
    public ResponseEntity<String> classesList(HttpSession session) {

        try {
            String phoneNo = (String) session.getAttribute("phoneNo");
            List<ClassesDto> response = mis.classList(phoneNo);

            String jsonResponse = om.writeValueAsString(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("JsonProcessingException", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

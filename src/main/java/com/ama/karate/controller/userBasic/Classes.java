package com.ama.karate.controller.userBasic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.UserInterfaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@RestController
public class Classes {

    @Autowired UserInterfaceService iis;

    @Autowired ObjectMapper om;
    
    @PostMapping("/user-classes")
    public ResponseEntity<String> instructorClasses(HttpSession session) {

        try {
            String phoneNo = (String) session.getAttribute("phoneNo");
            List<ClassesDto> response = iis.bringUserClasses(phoneNo);

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/class-studnets")
    public ResponseEntity<String> classStudnets(@RequestParam int classId, HttpSession session) {

        try {
            String phoneNo = (String) session.getAttribute("phoneNo");
            List<StudentDto> response = iis.bringClassStudents(phoneNo, classId);

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

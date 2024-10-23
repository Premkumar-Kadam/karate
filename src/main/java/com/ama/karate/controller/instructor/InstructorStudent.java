package com.ama.karate.controller.instructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.InstructorInterfaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@RestController
public class InstructorStudent {

    @Autowired InstructorInterfaceService iis;

    @Autowired ObjectMapper om;
    
    @PostMapping("/studnet-details")
    public ResponseEntity<String> studentDetails(@RequestParam int studentId, HttpSession session) {

        try {
            String phoneNo = (String) session.getAttribute("phoneNo");
            List<StudentDto> response = iis.bringStudentDetails(phoneNo, studentId);

            String jsonResponse = om.writeValueAsString(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("JsonProcessingException", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/student-admissions")
    public ResponseEntity<String> studentAdmissions(@RequestBody String StundetJson) {

        try {
            List<StudentDto> response = iis.sendStudentAdmissions(StundetJson);

            String jsonResponse = om.writeValueAsString(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("JsonProcessingException", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

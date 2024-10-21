package com.ama.karate.controller.instructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.InstructorInterfaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@RestController
public class InstructorClasses {

    @Autowired InstructorInterfaceService iis;

    @Autowired ObjectMapper om;
    
    @PostMapping("/instructor-classes")
    public ResponseEntity<String> instructorClasses(HttpSession session) {

        try {
            String phoneNo = (String) session.getAttribute("phoneNo");
            List<ClassesDto> response = iis.bringInstructorClasses(phoneNo);

            String jsonResponse = om.writeValueAsString(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("JsonProcessingException", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/class-studnets")
    public ResponseEntity<String> classStudnets(@RequestBody int classId, HttpSession session) {

        try {
            String phoneNo = (String) session.getAttribute("phoneNo");
            List<StudentDto> response = iis.bringClassStudents(phoneNo, classId);

            String jsonResponse = om.writeValueAsString(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("JsonProcessingException", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/studnet-details")
    public ResponseEntity<String> studentDetails(@RequestBody int studentId, HttpSession session) {

        try {
            String phoneNo = (String) session.getAttribute("phoneNo");
            List<StudentDto> response = iis.bringStudentDetails(phoneNo, studentId);

            String jsonResponse = om.writeValueAsString(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("JsonProcessingException", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

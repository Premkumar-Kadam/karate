package com.ama.karate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ama.karate.dao.instructor.InstructorClassesDao;
import com.ama.karate.dao.instructor.InstructorStudentDao;
import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.InstructorInterfaceService;

import jakarta.servlet.http.HttpSession;

public class InstructorService implements InstructorInterfaceService{

    @Autowired InstructorClassesDao icd;
    
    @Autowired InstructorStudentDao isd;

    @Override
    public List<ClassesDto> bringInstructorClasses(String phoneNo) {
        return icd.bringInstructorClasses(phoneNo);
    }

    @Override
    public List<StudentDto> bringClassStudents(String phoneNo, int classId) {
        return icd.bringClassStudents(phoneNo, classId);
    }

    @Override
    public List<StudentDto> bringStudentDetails(String phoneNo, int studentId) {
        return isd.bringStudentDetails(phoneNo, studentId);
    }

    @Override
    public List<StudentDto> sendStudentAdmissions(String StudentObj, HttpSession session) {
        String phoneNo = (String) session.getAttribute("phoneNo");
        return isd.sendStudentAdmissions(StudentObj, phoneNo);
    }

    
}

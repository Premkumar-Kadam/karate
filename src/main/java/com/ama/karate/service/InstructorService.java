package com.ama.karate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ama.karate.dao.InstructorClassesDao;
import com.ama.karate.dao.InstructorStudentDao;
import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.InstructorInterfaceService;

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

    
}

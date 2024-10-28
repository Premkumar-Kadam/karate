package com.ama.karate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ama.karate.dao.userBasic.ClassesDao;
import com.ama.karate.dao.userBasic.StudentsDao;
import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.UserInterfaceService;

import jakarta.servlet.http.HttpSession;

@Service
public class ClassesService implements UserInterfaceService{

    @Autowired ClassesDao icd;
    
    @Autowired StudentsDao isd;

    @Override
    public List<ClassesDto> bringUserClasses(String phoneNo) {
        return icd.bringUserClasses(phoneNo);
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

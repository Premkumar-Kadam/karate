package com.ama.karate.interfaceService;

import java.util.List;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.StudentDto;

import jakarta.servlet.http.HttpSession;

public interface InstructorInterfaceService {

    List<ClassesDto> bringInstructorClasses(String phoneNo);

    List<StudentDto> bringClassStudents(String phoneNo, int classId);

    List<StudentDto> bringStudentDetails(String phoneNo, int studentId);

    public List<StudentDto> sendStudentAdmissions(String StudentObj, HttpSession session);
    
}

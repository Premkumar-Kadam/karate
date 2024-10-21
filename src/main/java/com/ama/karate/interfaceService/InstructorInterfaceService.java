package com.ama.karate.interfaceService;

import java.util.List;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.StudentDto;

public interface InstructorInterfaceService {

    List<ClassesDto> bringInstructorClasses(String phoneNo);

    List<StudentDto> bringClassStudents(String phoneNo, int classId);

    List<StudentDto> bringStudentDetails(String phoneNo, int studentId);
    
}

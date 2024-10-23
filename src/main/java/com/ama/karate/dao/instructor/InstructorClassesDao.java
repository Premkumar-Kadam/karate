package com.ama.karate.dao.instructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.InstructorInterfaceService;

@Service
public class InstructorClassesDao{

    @Autowired
    JdbcTemplate jt;

    // Get List of classes for particular instructors
    public List<ClassesDto> bringInstructorClasses(String phoneNo) {
        try {
            String SQL = "";

            return jt.queryForList(SQL, ClassesDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<StudentDto> bringClassStudents(String phoneNo, int classId) {
        try {
            String SQL = "";
            return jt.queryForList(SQL, StudentDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }
}

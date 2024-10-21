package com.ama.karate.dao;

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
public class InstructorDao implements InstructorInterfaceService{

    @Autowired JdbcTemplate jt;

    //Get List of classes for perticular instructors
    @Override
    public List<ClassesDto> bringInstructorClasses(String phoneNo) {
        try {
            String SQL = "";

            return jt.queryForList(SQL, ClassesDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<ClassesDto>();
        }

    }

    @Override
    public List<StudentDto> bringClassStudents(String phoneNo, int classId) {
        try {
            String SQL = "";

            return jt.queryForList(SQL, StudentDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<StudentDto>();
        }

    }

    @Override
    public List<StudentDto> bringStudentDetails(String phoneNo, int studentId) {
        try {
            String SQL = "";

            return jt.queryForList(SQL, StudentDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<StudentDto>();
        }

    }

}

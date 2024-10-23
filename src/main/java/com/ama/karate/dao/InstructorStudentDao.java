package com.ama.karate.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ama.karate.dto.StudentDto;

public class InstructorStudentDao{

    @Autowired
    JdbcTemplate jt;

    public List<StudentDto> bringStudentDetails(String phoneNo, int studentId) {
        try {
            String SQL = "";

            return jt.queryForList(SQL, StudentDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }
}

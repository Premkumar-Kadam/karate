package com.ama.karate.dao.userBasic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.StudentDto;

@Service
public class StudentsDao{

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

    public List<StudentDto> sendStudentAdmissions(String StudentObj, String phoneNo) {
        try {
            String SQL = "";

            return jt.queryForList(SQL, StudentDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }
}

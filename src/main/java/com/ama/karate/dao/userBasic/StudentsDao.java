package com.ama.karate.dao.userBasic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.ResponseDto;
import com.ama.karate.dto.StudentDto;

@Service
public class StudentsDao{

    @Autowired
    JdbcTemplate jt;

    ResponseDto response = new ResponseDto();
    public List<StudentDto> bringStudentDetails(String phoneNo, int studentId) {
        try {
            String SQL = "";

            return jt.queryForList(SQL, StudentDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }

    public ResponseDto sendStudentAdmissions(String studentObj, String phoneNo) {
        try {
            String sql = "SELECT student_creation(?, ?)";
    
            String responseObj = jt.queryForObject(sql, String.class,studentObj, phoneNo);
    
            response.setStatusCode(201);
            response.setMessage(responseObj);
            return response;
        } catch (DataAccessException e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }
}
